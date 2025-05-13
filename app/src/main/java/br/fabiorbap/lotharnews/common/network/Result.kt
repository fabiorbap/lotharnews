package br.fabiorbap.lotharnews.common.network

sealed class Result {
    data object Success: Result()
    data class Failure(val e: Exception): Result()
}