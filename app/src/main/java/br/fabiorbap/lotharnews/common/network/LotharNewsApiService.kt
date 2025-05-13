package br.fabiorbap.lotharnews.common.network

import br.fabiorbap.lotharnews.news.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LotharNewsApiService {

    @GET("v2/everything")
    suspend fun getAllNews(@Query("page") page: Int = 1): NewsResponse

}