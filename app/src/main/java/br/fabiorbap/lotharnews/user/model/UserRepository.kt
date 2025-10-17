package br.fabiorbap.lotharnews.user.model

import br.fabiorbap.lotharnews.article.model.ArticleDao
import br.fabiorbap.lotharnews.article.model.ArticleEntity
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class UserRepository(val articleDao: ArticleDao) {

    fun observeFavorites(): Flow<List<ArticleEntity>> {
        return articleDao.observeFavorites()
    }

    suspend fun addFavorite(id: String) {
        val article = articleDao.getArticle(id)
        articleDao.insertFavorite(article)
    }

    suspend fun removeFavorite(id: String) {
        val article = articleDao.getArticle(id)
        articleDao.removeFromFavorites(article)
    }

}