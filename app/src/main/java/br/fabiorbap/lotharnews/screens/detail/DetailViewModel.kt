package br.fabiorbap.lotharnews.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.fabiorbap.lotharnews.article.model.Article
import br.fabiorbap.lotharnews.article.usecase.GetArticleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    val getArticleUseCase: GetArticleUseCase): ViewModel() {

    private val id: String? = savedStateHandle.get<String>("id")
    private lateinit var article: Article
    private val _uiState = MutableStateFlow(DetailState())
    val uiState: StateFlow<DetailState> = _uiState

    init {
        getArticle()
    }

    private fun getArticle() = viewModelScope.launch {
        article = getArticleUseCase(id ?: "")
        updateState()
    }

    private fun updateState(){
        _uiState.update {
            it.copy(
                article = article
            )
        }
    }

}