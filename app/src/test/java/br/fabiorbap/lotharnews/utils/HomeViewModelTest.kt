package br.fabiorbap.lotharnews.utils

import br.fabiorbap.lotharnews.article.usecase.GetArticlesUseCase
import br.fabiorbap.lotharnews.article.usecase.ObserveArticlesUseCase
import br.fabiorbap.lotharnews.common.network.response.Result
import br.fabiorbap.lotharnews.screens.home.HomeIntent
import br.fabiorbap.lotharnews.screens.home.HomeViewModel
import br.fabiorbap.lotharnews.user.usecase.ToggleFavoriteUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.TestScope
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class HomeViewModelTest {

    @MockK
    private lateinit var getArticlesUseCase: GetArticlesUseCase

    @MockK
    private lateinit var observeArticlesUseCase: ObserveArticlesUseCase

    @MockK
    private lateinit var toggleFavoriteUseCase: ToggleFavoriteUseCase

    @InjectMockKs
    private lateinit var SUT: HomeViewModel

    private lateinit var scope: TestScope

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        scope = TestScope()
    }

    @Test
    fun handleIntent_getNews_isLoadingTrue() {
        SUT.handleIntent(HomeIntent.GetNews)
        assertTrue(SUT.uiState.value.isLoading)
    }

    @Test
    fun handleIntent_getNews_success() {
        val state = SUT.uiState.value
        coEvery { getArticlesUseCase() } returns Result.Success
        SUT.handleIntent(HomeIntent.GetNews)
        assertTrue(state.isLoading)
        assertEquals(null, state.error)
    }

    @Test
    fun handleIntent_getNews_error() {
        val state = SUT.uiState.value
        SUT.handleIntent(HomeIntent.GetNews)
        val exception = mockk<Exception>()
        coEvery { getArticlesUseCase() } returns Result.Failure(exception)
    }

}