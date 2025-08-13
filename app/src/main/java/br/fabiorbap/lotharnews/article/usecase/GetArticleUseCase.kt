package br.fabiorbap.lotharnews.article.usecase

import br.fabiorbap.lotharnews.article.model.Article
import br.fabiorbap.lotharnews.article.model.ArticleRepository
import org.koin.core.annotation.Factory

@Factory
class GetArticleUseCase(val articleRepository: ArticleRepository) {

    suspend operator fun invoke(id: String): Article {
        return articleRepository.getArticle(id)
    }
}