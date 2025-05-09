package br.fabiorbap.lotharnews.common.networking

import br.fabiorbap.lotharnews.news.NewsApiModel
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface LotharNewsApiService {

    @GET
    fun getAllNews(): Flow<NewsApiModel>

}