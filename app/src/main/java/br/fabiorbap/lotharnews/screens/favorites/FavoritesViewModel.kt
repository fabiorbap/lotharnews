package br.fabiorbap.lotharnews.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.fabiorbap.lotharnews.article.model.Article
import br.fabiorbap.lotharnews.user.usecase.ObserveFavoritesUseCase
import br.fabiorbap.lotharnews.user.usecase.RemoveFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class FavoritesViewModel(val observeFavoritesUseCase: ObserveFavoritesUseCase,
    val removeFavoriteUseCase: RemoveFavoriteUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesState())
    val uiState: StateFlow<FavoritesState> = _uiState
    private var articles: List<Article>? = null

    init {
        observeFavorites()
    }

    fun handleIntent(intent: FavoritesIntent) {
        when(intent) {
            is FavoritesIntent.RemoveFavorite -> removeFavorite(intent.id)
        }
    }

    private fun observeFavorites() = viewModelScope.launch {
        observeFavoritesUseCase().collect {
            articles = it
            updateState()
        }
    }

    private fun removeFavorite(id: String) = viewModelScope.launch {
        removeFavoriteUseCase(id)
    }

    private fun updateState() {
        _uiState.update {
            it.copy(
                articles = articles
            )
        }
    }

}