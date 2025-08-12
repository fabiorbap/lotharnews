package br.fabiorbap.lotharnews.article.usecase

import br.fabiorbap.lotharnews.article.model.ArticleRepository
import org.koin.core.annotation.Factory

@Factory
class GetArticleUseCase(val articleRepository: ArticleRepository) {

    operator fun invoke(id: String) {
        articleRepository.getArticle(id)
    }
}