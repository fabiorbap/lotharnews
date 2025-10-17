package br.fabiorbap.lotharnews.common.network.response

sealed class Result {
    data object Success: Result()
    data class Failure(val e: Exception): Result()
}