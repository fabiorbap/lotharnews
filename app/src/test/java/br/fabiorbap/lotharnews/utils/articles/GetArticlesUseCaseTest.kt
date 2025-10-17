@file:OptIn(ExperimentalCoroutinesApi::class)

package br.fabiorbap.lotharnews.utils.articles

import br.fabiorbap.lotharnews.article.model.ArticleRepository
import br.fabiorbap.lotharnews.article.usecase.GetArticlesUseCase
import br.fabiorbap.lotharnews.common.network.response.Result
import br.fabiorbap.lotharnews.utils.common.MainDispatcherRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GetArticlesUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @RelaxedMockK
    lateinit var articleRepository: ArticleRepository

    @InjectMockKs
    lateinit var SUT: GetArticlesUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
    }

    @Test
    fun getArticles_articlesAvailable_successReturned() = runTest {
        coEvery { articleRepository.getArticles() } returns Unit

        val result = SUT()

        assertEquals(Result.Success, result)
    }

    @Test
    fun getArticles_exceptionThrown_errorReturned() = runTest {
        val exception = Exception()
        coEvery { articleRepository.getArticles() } throws exception

        val result = SUT()

        assertEquals(Result.Failure(exception), result)
    }

}