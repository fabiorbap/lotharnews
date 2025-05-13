package br.fabiorbap.lotharnews.common.network

import br.fabiorbap.lotharnews.news.model.NewsResponse
import retrofit2.http.GET

interface LotharNewsApiService {

    @GET("v2/everything")
    suspend fun getAllNews(): NewsResponse

}