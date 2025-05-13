package br.fabiorbap.lotharnews.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.fabiorbap.lotharnews.common.network.Result
import br.fabiorbap.lotharnews.news.model.News
import br.fabiorbap.lotharnews.news.usecase.GetNewsUseCase
import br.fabiorbap.lotharnews.news.usecase.ObserveNewsUseCase
import br.fabiorbap.lotharnews.screens.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(private val getNewsUseCase: GetNewsUseCase,
    private val observeNewsUseCase: ObserveNewsUseCase): ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private var news: News? = null

    init {
        observeNews()
        getNews()
    }

    private fun getNews() = viewModelScope.launch {
        when(getNewsUseCase()){
            Result.Success -> return@launch
            is Result.Error -> {}
        }
    }

    private fun observeNews() = viewModelScope.launch {
        observeNewsUseCase().collect {
            news = it
            updateState()
        }
    }

    private fun updateState() {
        _uiState.update { UiState.Content(content = news) }
    }

}