package br.fabiorbap.lotharnews.utils.articles

import br.fabiorbap.lotharnews.article.model.ArticleDao
import br.fabiorbap.lotharnews.article.model.ArticleRepository
import br.fabiorbap.lotharnews.article.model.NewsResponse
import br.fabiorbap.lotharnews.common.network.LotharNewsApiService
import br.fabiorbap.lotharnews.utils.common.MainDispatcherRule
import br.fabiorbap.lotharnews.utils.common.mockArticleEntities
import br.fabiorbap.lotharnews.utils.common.mockArticlesResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertFailsWith

@RunWith(JUnit4::class)
class ArticleRepositoryTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @InjectMockKs
    lateinit var articleRepository: ArticleRepository

    @RelaxedMockK
    lateinit var lotharNewsApiService: LotharNewsApiService

    @RelaxedMockK
    lateinit var articleDao: ArticleDao

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
    }

    @Test
    fun getArticles_called_apiCalled() = runTest {
        articleRepository.getArticles()

        coVerify { lotharNewsApiService.getAllNews() }
    }

    @Test
    fun getArticles_called_daoCalled() = runTest {
        articleRepository.getArticles()

        coVerify { articleDao.updateArticles(any()) }
    }

    @Test
    fun getArticles_apiError_daoNotCalled() = runTest {
        mockAllNewsApiError()

        assertFailsWith<Exception> {
            articleRepository.getArticles()
        }

        coVerify(exactly = 0) { articleDao.updateArticles(any()) }
    }

    @Test
    fun getArticles_apiSuccess_articlesStoredInDao() = runTest {
        mockAllNewsApiSuccess()

        articleRepository.getArticles()

        coVerify { articleDao.updateArticles(mockArticleEntities) }
    }

    private fun mockAllNewsApiSuccess() {
        coEvery { lotharNewsApiService.getAllNews() } returns NewsResponse(totalResults = 10,
            mockArticlesResponse)
    }

    private fun mockAllNewsApiError() {
        coEvery { lotharNewsApiService.getAllNews() } throws Exception()
    }

}