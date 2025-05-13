package br.fabiorbap.lotharnews.common.network

import br.fabiorbap.lotharnews.common.network.ApiError.GenericError
import br.fabiorbap.lotharnews.common.network.ApiError.ServerUnavailable
import br.fabiorbap.lotharnews.common.network.ApiError.Unauthorized
import retrofit2.HttpException

enum class HttpErrorCodes(val errorCode: Int) {
    Unauthorized(401),
    ServerUnavailable(500)
}

sealed class ApiError(private val message: String) {
    data object Unauthorized : ApiError("Unauthorized")
    data object ServerUnavailable : ApiError("Server unavailable")
    data object GenericError : ApiError("Generic error")
}

fun mapToError(e: HttpException): ApiError {
    return when (e.code()) {
        HttpErrorCodes.Unauthorized.errorCode -> Unauthorized
        HttpErrorCodes.ServerUnavailable.errorCode -> ServerUnavailable
        else -> GenericError
    }
}