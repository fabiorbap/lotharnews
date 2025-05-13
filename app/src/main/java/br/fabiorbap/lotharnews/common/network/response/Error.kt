package br.fabiorbap.lotharnews.common.network.response

import br.fabiorbap.lotharnews.common.network.response.Error.GenericError
import br.fabiorbap.lotharnews.common.network.response.Error.ServerUnavailable
import br.fabiorbap.lotharnews.common.network.response.Error.Unauthorized
import retrofit2.HttpException

enum class HttpErrorCodes(val errorCode: Int) {
    Unauthorized(401),
    ServerUnavailable(500)
}

sealed class Error(private val message: String) {
    data object Unauthorized : Error("Unauthorized")
    data object ServerUnavailable : Error("Server unavailable")
    data class GenericError(val e: Exception) : Error(e.message ?: "Generic Error")
}

fun mapToError(e: Exception): Error {
    if (e is HttpException) {
        return when (e.code()) {
            HttpErrorCodes.Unauthorized.errorCode -> Unauthorized
            HttpErrorCodes.ServerUnavailable.errorCode -> ServerUnavailable
            else -> GenericError(e)
        }
    }
    return GenericError(e)
}