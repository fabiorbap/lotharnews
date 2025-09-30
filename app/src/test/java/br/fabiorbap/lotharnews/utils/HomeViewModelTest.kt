package br.fabiorbap.lotharnews.utils

import br.fabiorbap.lotharnews.article.model.Article
import br.fabiorbap.lotharnews.article.model.Source
import br.fabiorbap.lotharnews.article.usecase.GetArticlesUseCase
import br.fabiorbap.lotharnews.article.usecase.ObserveArticlesUseCase
import br.fabiorbap.lotharnews.common.network.response.Error
import br.fabiorbap.lotharnews.common.network.response.HttpErrorCodes
import br.fabiorbap.lotharnews.common.network.response.Result
import br.fabiorbap.lotharnews.screens.home.HomeIntent
import br.fabiorbap.lotharnews.screens.home.HomeViewModel
import br.fabiorbap.lotharnews.user.usecase.ToggleFavoriteUseCase
import io.mockk.MockKAnnotations
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
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
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @RelaxedMockK
    private lateinit var getArticlesUseCase: GetArticlesUseCase

    @RelaxedMockK
    private lateinit var observeArticlesUseCase: ObserveArticlesUseCase

    @RelaxedMockK
    private lateinit var toggleFavoriteArticleUseCase: ToggleFavoriteUseCase

    private lateinit var SUT: HomeViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun getArticles_initialState_stateInitialized() = runTest {
        val deferrableResult = CompletableDeferred<Result>()
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

    @Test
    fun getArticles_authInvalid_unauthorizedErrorReturned() = runTest {
        mockUnauthorizedErrorOnGetArticles()
        initializeViewModel()

        advanceUntilIdle()

        with(SUT.uiState.value) {
            assertFalse(isLoading)
            assertEquals(null, articles)
            assertEquals(Error.Unauthorized, error)
        }

    }

    @Test
    fun getArticles_serverUnavailable_serverUnavailableReturned() = runTest {
        mockServerUnavailableOnGetArticles()
        initializeViewModel()

        advanceUntilIdle()

        with(SUT.uiState.value) {
            assertFalse(isLoading)
            assertEquals(null, articles)
            assertEquals(Error.ServerUnavailable, error)
        }
    }

    @Test
    fun getArticles_randomError_genericErrorReturned() = runTest {
        mockGenericErrorOnGetArticles()
        initializeViewModel()

        advanceUntilIdle()

        with(SUT.uiState.value) {
            assertFalse(isLoading)
            assertEquals(null, articles)
            assert(error is Error.GenericError)
        }
    }

    @Test
    fun handleIntent_getArticlesIntentReceived_getArticlesCalled() = runTest {
        initializeViewModel()

        advanceUntilIdle()

        clearMocks(getArticlesUseCase) //getArticlesUseCase is called when the VM initializes, so we want to clear this interaction

        SUT.handleIntent(HomeIntent.GetArticles)

        advanceUntilIdle()

        coVerify(exactly = 1) { getArticlesUseCase() }
    }

    //handleIntent calls toggleFavorite

    @Test
    fun handleIntent_setFavoriteIntentReceived_toggleFavoriteCalled() = runTest {
        initializeViewModel()

        val id = ""

        SUT.handleIntent(HomeIntent.FavoriteIconClicked(id))

        advanceUntilIdle()

        coVerify { toggleFavoriteArticleUseCase(id) }

    }

    //handleIntent receives intent to favorite article
    //handleIntent receives intent to unfavorite article

    private fun initializeViewModel() {
        SUT = HomeViewModel(getArticlesUseCase, observeArticlesUseCase, toggleFavoriteArticleUseCase)
    }

    private fun mockFlowWithArticlesOnObserveArticles() {
        coEvery { observeArticlesUseCase() } returns flowOf(mockArticles)
    }

    private fun mockSuccessOnGetArticles() {
        coEvery { getArticlesUseCase() } returns Result.Success
    }

    private fun mockUnauthorizedErrorOnGetArticles() {
        val errorBody = "".toResponseBody("application/json".toMediaTypeOrNull())
        val unauthorizedException = HttpException(
            Response.error<Any>(
                HttpErrorCodes.Unauthorized.errorCode,
                errorBody
            )
        )
        coEvery { getArticlesUseCase() } returns Result.Failure(unauthorizedException)
    }

    private fun mockServerUnavailableOnGetArticles() {
        val errorBody = "".toResponseBody("application/json".toMediaTypeOrNull())
        val serverUnavailableException = HttpException(Response.error<Any>(HttpErrorCodes.ServerUnavailable.errorCode,
            errorBody))
        coEvery { getArticlesUseCase() } returns Result.Failure(serverUnavailableException)
    }

    private fun mockGenericErrorOnGetArticles() {
        val errorBody = "".toResponseBody("application/json".toMediaTypeOrNull())
        val genericException = HttpException(Response.error<Any>(400, errorBody))
        coEvery { getArticlesUseCase() } returns Result.Failure(genericException)
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
                "Chris MasonPolitical editor\r\nZack Polanski's sweary, brash and blunt victory video on social media said everything about how the Green Party of England and Wales is under new leadership.\r\nHis landsli… [+2577 chars]",
                false
            ),
            Article(
                source = Source(null, "BBC News"),
                null,
                "Chris Mason: How Polanski's Green leadership could impact UK politics",
                "Zack Polanski's landslide victory is the latest example of a shake up in the country's politics.",
                "https://www.bbc.com/news/articles/c9d0d32q0eno",
                "https://ichef.bbci.co.uk/news/1024/branded_news/9ea5/live/3d188cc0-8847-11f0-b391-6936825093bd.jpg",
                "2025-09-02T22:49:49Z",
                "Chris MasonPolitical editor\r\nZack Polanski's sweary, brash and blunt victory video on social media said everything about how the Green Party of England and Wales is under new leadership.\r\nHis landsli… [+2577 chars]",
                false
            )
        )
    }

}