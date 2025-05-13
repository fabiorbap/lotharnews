package br.fabiorbap.lotharnews.news.usecase

import br.fabiorbap.lotharnews.common.network.Result
import br.fabiorbap.lotharnews.common.network.LotharNewsApiService
import br.fabiorbap.lotharnews.common.network.mapToError
import br.fabiorbap.lotharnews.news.model.NewsDAO
import br.fabiorbap.lotharnews.news.model.toNews
import org.koin.core.annotation.Factory
import retrofit2.HttpException

@Factory
class GetNewsUseCase(private val lotharNewsApiService: LotharNewsApiService,
    private val newsDAO: NewsDAO) {

    suspend operator fun invoke(): Result {
       return  try {
            val result = lotharNewsApiService.getAllNews()
            newsDAO.setNews(result.toNews())
            Result.Success
        } catch (e: HttpException) {
            Result.Error(mapToError(e))
        }
    }
}