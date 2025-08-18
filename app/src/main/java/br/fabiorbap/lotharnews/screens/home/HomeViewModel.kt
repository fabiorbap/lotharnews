package br.fabiorbap.lotharnews.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.fabiorbap.lotharnews.article.model.Article
import br.fabiorbap.lotharnews.common.network.response.Error
import br.fabiorbap.lotharnews.common.network.response.Result
import br.fabiorbap.lotharnews.common.network.response.mapToError
import br.fabiorbap.lotharnews.article.usecase.GetArticlesUseCase
import br.fabiorbap.lotharnews.article.usecase.ObserveArticlesUseCase
import br.fabiorbap.lotharnews.user.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(
    private val getArticlesUseCase: GetArticlesUseCase,
    private val observeArticlesUseCase: ObserveArticlesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState

    private var articles: List<Article>? = null
    private var isLoading: Boolean = true
    private var error: Error? = null

    init {
        observeArticles()
        getArticles()
    }

    fun handleIntent(intent: HomeIntent) {
        when(intent) {
            HomeIntent.GetNews -> getArticles()
            is HomeIntent.FavoriteIconClicked -> onFavoriteClick(intent.id)
        }
    }

    private fun getArticles() = viewModelScope.launch {
        isLoading = true
        updateState()
        when (val result = getArticlesUseCase()) {
            Result.Success -> {
                isLoading = false
                error = null
                updateState()
            }
            is Result.Failure -> {
                isLoading = false
                error = mapToError(result.e)
                updateState()
            }
        }
    }

    private fun observeArticles() = viewModelScope.launch {
        observeArticlesUseCase().collect {
            articles = it
            updateState()
        }
    }

    private fun onFavoriteClick(id: String) = viewModelScope.launch {
        toggleFavoriteUseCase(id)
    }

    private fun updateState() {
        _uiState.update {
            it.copy(
                isLoading = isLoading,
                articles = articles,
                error = error
            )
        }
    }

}