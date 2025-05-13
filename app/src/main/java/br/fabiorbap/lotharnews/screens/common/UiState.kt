package br.fabiorbap.lotharnews.screens.common

import br.fabiorbap.lotharnews.common.network.ApiError

sealed class UiState {

    data class Content<T>(val content: T): UiState()
    data class Error(val error: ApiError): UiState()
    data object Loading: UiState()

}