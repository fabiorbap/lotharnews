package br.fabiorbap.lotharnews.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.fabiorbap.lotharnews.common.network.response.Error
import br.fabiorbap.lotharnews.common.network.response.Result
import br.fabiorbap.lotharnews.common.network.response.mapToError
import br.fabiorbap.lotharnews.news.model.News
import br.fabiorbap.lotharnews.news.usecase.GetNewsUseCase
import br.fabiorbap.lotharnews.news.usecase.ObserveNewsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(
    private val getNewsUseCase: GetNewsUseCase,
    private val observeNewsUseCase: ObserveNewsUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState

    private var news: News? = null
    private var isLoading: Boolean = true
    private var error: Error? = null

    init {
        observeNews()
        getNews()
    }

    fun handleIntent(intent: HomeIntent) {
        when(intent) {
            HomeIntent.GetNews -> getNews()
        }
    }

    private fun getNews() = viewModelScope.launch {
        updateState()
        when (val result = getNewsUseCase()) {
            Result.Success -> {
                isLoading = false
                updateState()
            }
            is Result.Failure -> {
                isLoading = false
                error = mapToError(result.e)
                updateState()
            }
        }
    }

    private fun observeNews() = viewModelScope.launch {
        observeNewsUseCase().collect {
            news = it
            updateState()
        }
    }

    private fun updateState() {
        _uiState.update {
            it.copy(
                isLoading = isLoading,
                news = news,
                error = error
            )
        }
    }

}