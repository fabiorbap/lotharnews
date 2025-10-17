package br.fabiorbap.lotharnews.utils.articles

import app.cash.turbine.test
import br.fabiorbap.lotharnews.article.model.ArticleDao
import br.fabiorbap.lotharnews.article.model.ArticleRepository
import br.fabiorbap.lotharnews.article.model.NewsResponse
import br.fabiorbap.lotharnews.common.network.LotharNewsApiService
import br.fabiorbap.lotharnews.utils.common.MainDispatcherRule
import br.fabiorbap.lotharnews.utils.common.mockArticleEntities
import br.fabiorbap.lotharnews.utils.common.mockArticles
import br.fabiorbap.lotharnews.utils.common.mockArticlesResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@RunWith(JUnit4::class)
class ArticleRepositoryTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @InjectMockKs
    lateinit var SUT: ArticleRepository

    @RelaxedMockK
    lateinit var lotharNewsApiService: LotharNewsApiService

    @RelaxedMockK
    lateinit var articleDao: ArticleDao

    companion object {
        const val ARTICLE_ID = "1"
    }

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
    }

    @Test
    fun getArticles_called_apiCalled() = runTest {
        SUT.getArticles()

        coVerify { lotharNewsApiService.getAllNews() }
    }

    @Test
    fun getArticles_called_daoCalled() = runTest {
        SUT.getArticles()

        coVerify { articleDao.updateArticles(any()) }
    }

    @Test
    fun getArticles_apiError_daoNotCalled() = runTest {
        mockAllNewsApiError()

        assertFailsWith<Exception> {
            SUT.getArticles()
        }

        coVerify(exactly = 0) { articleDao.updateArticles(any()) }
    }

    @Test
    fun getArticles_apiSuccess_articlesStoredInDao() = runTest {
        mockAllNewsApiSuccess()

        SUT.getArticles()

        coVerify { articleDao.updateArticles(mockArticleEntities) }
    }

    @Test
    fun observeArticles_called_daoCalled() = runTest {
        SUT.observeArticles()

        coVerify(exactly = 1) { articleDao.observeArticles() }
    }

    @Test
    fun observeArticles_called_flowReturned() = runTest {
        coEvery { articleDao.observeArticles() } returns flowOf(mockArticleEntities)

        SUT.observeArticles().test {
            val result = awaitItem()
            assertEquals(mockArticleEntities, result)
            awaitComplete()
        }
    }

    @Test
    fun getArticle_called_daoCalled() = runTest {
        SUT.getArticle(ARTICLE_ID)

        coVerify(exactly = 1) { articleDao.getArticle(ARTICLE_ID) }
    }

    @Test
    fun getArticle_called_articleReturned() = runTest {
        coEvery { articleDao.getArticle(ARTICLE_ID) } returns mockArticleEntities[0]

        val result = SUT.getArticle(ARTICLE_ID)

        assertEquals(mockArticles[0], result)
    }

    private fun mockAllNewsApiSuccess() {
        coEvery { lotharNewsApiService.getAllNews() } returns NewsResponse(totalResults = 10,
            mockArticlesResponse)
    }

    private fun mockAllNewsApiError() {
        coEvery { lotharNewsApiService.getAllNews() } throws Exception()
    }

}