package br.fabiorbap.lotharnews.article.usecase

import br.fabiorbap.lotharnews.article.model.Article
import br.fabiorbap.lotharnews.article.model.ArticleRepository
import br.fabiorbap.lotharnews.article.model.toModels
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

@Factory
class ObserveArticlesUseCase(private val articleRepository: ArticleRepository) {

    operator fun invoke(): Flow<List<Article>> {
        return articleRepository.observeArticles().map { it.toModels() }
    }
}