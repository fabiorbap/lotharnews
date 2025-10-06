package br.fabiorbap.lotharnews.utils.home

import br.fabiorbap.lotharnews.article.model.Article
import br.fabiorbap.lotharnews.article.usecase.GetArticlesUseCase
import br.fabiorbap.lotharnews.article.usecase.ObserveArticlesUseCase
import br.fabiorbap.lotharnews.common.network.response.Error
import br.fabiorbap.lotharnews.common.network.response.HttpErrorCodes
import br.fabiorbap.lotharnews.common.network.response.Result
import br.fabiorbap.lotharnews.screens.home.HomeIntent
import br.fabiorbap.lotharnews.screens.home.HomeViewModel
import br.fabiorbap.lotharnews.user.usecase.ToggleFavoriteUseCase
import br.fabiorbap.lotharnews.utils.common.MainDispatcherRule
import br.fabiorbap.lotharnews.utils.common.mockArticles
import io.mockk.MockKAnnotations
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
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
    fun observeArticles_articlesUpdated_stateUpdated() = runTest {
        val deferrable = CompletableDeferred<Unit>()
        coEvery { observeArticlesUseCase() } returns flow {
            emit(emptyList())
            deferrable.await()
            emit(mockArticles)
        }

        initializeViewModel()

        advanceUntilIdle()

        assertEquals(emptyList<Article>(), SUT.uiState.value.articles)

        deferrable.complete(Unit)
        advanceUntilIdle()

        assertEquals(mockArticles, SUT.uiState.value.articles)
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
        mockSuccessOnObserveArticles()
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

        clearMocks(
            getArticlesUseCase,
            answers = false
        )

        SUT.handleIntent(HomeIntent.GetArticles)

        advanceUntilIdle()

        coVerify(exactly = 1) { getArticlesUseCase() }
    }

    @Test
    fun handleIntent_setFavoriteIntentReceived_toggleFavoriteCalled() = runTest {
        initializeViewModel()

        val id = "id"

        SUT.handleIntent(HomeIntent.FavoriteIconClicked(id))

        advanceUntilIdle()

        coVerify { toggleFavoriteArticleUseCase(id) }

    }

    @Test
    fun handleIntent_retryGetArticlesAttempted_articlesReturned() = runTest {
        mockGenericErrorOnGetArticles()
        val deferrable = CompletableDeferred<Unit>()
        coEvery { observeArticlesUseCase() } returns flow {
            emit(emptyList())
            deferrable.await()
            emit(mockArticles)
        }

        initializeViewModel()

        advanceUntilIdle()

        mockSuccessOnGetArticles()

        SUT.handleIntent(HomeIntent.GetArticles)

        deferrable.complete(Unit)
        advanceUntilIdle()

        with(SUT.uiState.value) {
            assertEquals(mockArticles, articles)
            assertFalse(isLoading)
            assertEquals(null, error)
        }
    }

    private fun initializeViewModel() {
        SUT =
            HomeViewModel(getArticlesUseCase, observeArticlesUseCase, toggleFavoriteArticleUseCase)
    }

    private fun mockSuccessOnObserveArticles() {
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
        val serverUnavailableException = HttpException(
            Response.error<Any>(
                HttpErrorCodes.ServerUnavailable.errorCode,
                errorBody
            )
        )
        coEvery { getArticlesUseCase() } returns Result.Failure(serverUnavailableException)
    }

    private fun mockGenericErrorOnGetArticles() {
        val errorBody = "".toResponseBody("application/json".toMediaTypeOrNull())
        val genericException = HttpException(Response.error<Any>(400, errorBody))
        coEvery { getArticlesUseCase() } returns Result.Failure(genericException)
    }

}