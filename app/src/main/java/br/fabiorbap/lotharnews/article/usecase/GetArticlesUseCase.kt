package br.fabiorbap.lotharnews.article.usecase

import br.fabiorbap.lotharnews.common.network.response.Result
import br.fabiorbap.lotharnews.article.model.ArticleRepository
import org.koin.core.annotation.Factory

@Factory
class GetArticlesUseCase(private val articleRepository: ArticleRepository) {

    suspend operator fun invoke(): Result {
       return  try {
            articleRepository.getArticles()
            Result.Success
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }
}