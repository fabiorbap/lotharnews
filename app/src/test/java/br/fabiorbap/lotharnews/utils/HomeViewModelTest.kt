package br.fabiorbap.lotharnews.utils

import br.fabiorbap.lotharnews.article.model.Article
import br.fabiorbap.lotharnews.article.model.Source
import br.fabiorbap.lotharnews.article.usecase.GetArticlesUseCase
import br.fabiorbap.lotharnews.article.usecase.ObserveArticlesUseCase
import br.fabiorbap.lotharnews.common.network.response.Error
import br.fabiorbap.lotharnews.common.network.response.HttpErrorCodes
import br.fabiorbap.lotharnews.common.network.response.Result
import br.fabiorbap.lotharnews.screens.home.HomeViewModel
import br.fabiorbap.lotharnews.user.usecase.ToggleFavoriteUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var getArticlesUseCase: GetArticlesUseCase

    @MockK
    private lateinit var observeArticlesUseCase: ObserveArticlesUseCase

    @MockK
    private lateinit var toggleFavoriteUseCase: ToggleFavoriteUseCase

    private lateinit var SUT: HomeViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
    }

    @Test
    fun getArticles_initialState_stateInitialized() = runTest {
        val deferrableResult = CompletableDeferred<Result>()
        coEvery { observeArticlesUseCase() } returns flowOf()
        coEvery { getArticlesUseCase() } coAnswers { deferrableResult.await() }
        initializeViewModel()

        advanceUntilIdle()

        with(SUT.uiState.value) {
            assertTrue(isLoading)
            assertEquals(null, error)
            assertEquals(null, articles)
        }

        deferrableResult.complete(Result.Success)
        advanceUntilIdle()

    }

    //getArticles returns success with mockArticles

    @Test
    fun getArticles_initialState_articlesReturned() = runTest {
        initializeViewModel()
        mockFlowWithArticlesOnObserveArticles()
        mockSuccessOnGetArticles()

        advanceUntilIdle()

        with(SUT.uiState.value) {
            assertFalse(isLoading)
            assertEquals(mockArticles, articles)
            assertEquals(null, error)
        }
    }

    //getArticles returns an Unauthorized error

    @Test
    fun getArticles_authInvalid_unauthorizedErrorReturned() = runTest {
        mockUnauthorizedErrorOnGetArticles()
        mockEmptyFlowOnObserveArticles()
        initializeViewModel()

        advanceUntilIdle()

        with(SUT.uiState.value) {
            assertFalse(isLoading)
            assertEquals(null, articles)
            assertEquals(Error.Unauthorized, error)
        }

    }
    //getArticles returns a Server error

    @Test
    fun getArticles_serverUnavailable_serverUnavailableReturned() = runTest {
        mockEmptyFlowOnObserveArticles()
        mockServerUnavailableOnGetArticles()
        initializeViewModel()

        advanceUntilIdle()

        with(SUT.uiState.value) {
            assertFalse(isLoading)
            assertEquals(null, articles)
            assertEquals(Error.ServerUnavailable, error)
        }
    }

    //getArticles returns a generic error

//    @Test
//    fun handleIntent_getArticles_error() = runTest {
//        val state = SUT.uiState.value
//        SUT.handleIntent(HomeIntent.GetNews)
//        val exception = mockk<Exception>()
//        coEvery { getArticlesUseCase() } returns Result.Failure(exception)
//    }

    private fun initializeViewModel() {
        SUT = HomeViewModel(getArticlesUseCase, observeArticlesUseCase, toggleFavoriteUseCase)
    }

    private fun mockEmptyFlowOnObserveArticles() {
        coEvery { observeArticlesUseCase() } returns flowOf()
    }

    private fun mockFlowWithArticlesOnObserveArticles() {
        coEvery { observeArticlesUseCase() } returns flowOf(mockArticles)
    }

    private fun mockSuccessOnGetArticles() {
        coEvery { getArticlesUseCase() } returns Result.Success
    }

    private fun mockUnauthorizedErrorOnGetArticles() {
        val errorBody = "".toResponseBody("application/json".toMediaTypeOrNull())
        val unauthorizedException = retrofit2.HttpException(
            Response.error<Any>(
                HttpErrorCodes.Unauthorized.errorCode,
                errorBody
            )
        )
        coEvery { getArticlesUseCase() } returns Result.Failure(unauthorizedException)
    }

    private fun mockServerUnavailableOnGetArticles() {
        val errorBody = "".toResponseBody("application/json".toMediaTypeOrNull())
        val serverUnavailableException = retrofit2.HttpException(Response.error<Any>(HttpErrorCodes.ServerUnavailable.errorCode,
            errorBody))
        coEvery { getArticlesUseCase() } returns Result.Failure(serverUnavailableException)
    }

    companion object {
        val mockArticles = listOf(
            Article(
                source = Source(null, "BBC News"),
                null,
                "Chris Mason: How Polanski's Green leadership could impact UK politics",
                "Zack Polanski's landslide victory is the latest example of a shake up in the country's politics.",
                "https://www.bbc.com/news/articles/c9d0d32q0eno",
                "https://ichef.bbci.co.uk/news/1024/branded_news/9ea5/live/3d188cc0-8847-11f0-b391-6936825093bd.jpg",
                "2025-09-02T22:49:49Z",
                "Chris MasonPolitical editor\r\nZack Polanski's sweary, brash and blunt victory video on social media said everything about how the Green Party of England and Wales is under new leadership.\r\nHis landsli… [+2577 chars]"
            ),
            Article(
                source = Source(null, "BBC News"),
                null,
                "Chris Mason: How Polanski's Green leadership could impact UK politics",
                "Zack Polanski's landslide victory is the latest example of a shake up in the country's politics.",
                "https://www.bbc.com/news/articles/c9d0d32q0eno",
                "https://ichef.bbci.co.uk/news/1024/branded_news/9ea5/live/3d188cc0-8847-11f0-b391-6936825093bd.jpg",
                "2025-09-02T22:49:49Z",
                "Chris MasonPolitical editor\r\nZack Polanski's sweary, brash and blunt victory video on social media said everything about how the Green Party of England and Wales is under new leadership.\r\nHis landsli… [+2577 chars]"
            )
        )
    }

}