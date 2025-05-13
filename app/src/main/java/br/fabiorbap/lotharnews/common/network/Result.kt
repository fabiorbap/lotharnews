package br.fabiorbap.lotharnews.common.network

sealed class Result {
    data object Success: Result()
    data class Error(val e: ApiError): Result()
}