package br.fabiorbap.lotharnews.common.network

import br.fabiorbap.lotharnews.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val url = chain.request().url
        val newUrl = url.newBuilder().addQueryParameter("apiKey", BuildConfig.API_KEY).build()
        request.url(newUrl)
        return chain.proceed(request.build())
    }
}