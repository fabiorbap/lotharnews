package br.fabiorbap.lotharnews.utils.articles

import app.cash.turbine.test
import br.fabiorbap.lotharnews.article.model.Article
import br.fabiorbap.lotharnews.article.model.ArticleRepository
import br.fabiorbap.lotharnews.article.usecase.ObserveArticlesUseCase
import br.fabiorbap.lotharnews.utils.common.MainDispatcherRule
import br.fabiorbap.lotharnews.utils.common.mockArticleEntities
import br.fabiorbap.lotharnews.utils.common.mockArticles
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ObserveArticlesUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @RelaxedMockK
    lateinit var articleRepository: ArticleRepository

    @InjectMockKs
    lateinit var SUT: ObserveArticlesUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
    }

    @Test
    fun observeArticles_emptyArticleList_emptyListReturned() = runTest {
        coEvery { articleRepository.observeArticles() } returns flowOf(emptyList())

        SUT().test {
            val result = awaitItem()
            assertEquals(emptyList<Article>(), result)
            awaitComplete()
        }
    }

    @Test
    fun observeArticles_newArticles_articlesMappedReturned() = runTest {
        coEvery { articleRepository.observeArticles() } returns flowOf(mockArticleEntities)

        SUT().test {
            val result = awaitItem()
            assertEquals(mockArticles, result)
            awaitComplete()
        }
    }

    @Test
    fun observeArticles_multipleArticleEmissions_multipleArticleEmissionsMapped() = runTest {
        coEvery { articleRepository.observeArticles() } returns flowOf(mockArticleEntities, mockArticleEntities)

        SUT().test {
            val firstArticleBatch = awaitItem()
            assertEquals(mockArticles, firstArticleBatch)
            val secondArticleBatch = awaitItem()
            assertEquals(mockArticles, secondArticleBatch)
            awaitComplete()
        }
    }

}