package br.fabiorbap.lotharnews.article.model

import br.fabiorbap.lotharnews.common.network.LotharNewsApiService
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class ArticleRepository(private val articleDao: ArticleDao,
                        private val lotharNewsApiService: LotharNewsApiService) {

    suspend fun getArticles() {
        val articles = lotharNewsApiService.getAllNews().articles
        val articleEntities = articles?.toEntities() ?: listOf()
        articleDao.insertArticles(articleEntities)
    }

    fun observeArticles(): Flow<List<ArticleEntity>> {
        return articleDao.observeArticles()
    }

}