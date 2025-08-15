package br.fabiorbap.lotharnews.article.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArticles(articles: List<ArticleEntity>)

    @Query(
        "SELECT * FROM ArticleEntity"
    )
    fun observeArticles(): Flow<List<ArticleEntity>>

    @Query("DELETE FROM ArticleEntity")
    suspend fun deleteArticles()

    @Query(
        """
        SELECT 
        article.id,
        article.author,
        article.content,
        article.description,
        article.name,
        article.publishedAt,
        article.title,
        article.urlToImage,
        article.url,
        article.isFavorite
        FROM ArticleEntity article WHERE article.url = :id"""
    )
    suspend fun getArticle(id: String): ArticleEntity

    @Transaction
    suspend fun insertFavorite(articleEntity: ArticleEntity) {
        updateArticle(articleEntity.copy(isFavorite = true))
    }

    @Transaction
    suspend fun removeFromFavorites(articleEntity: ArticleEntity) {
        updateArticle(articleEntity.copy(isFavorite = false))
    }

    @Query(
        """
         SELECT * FROM ArticleEntity article WHERE isFavorite
    """
    )
    fun observeFavorites(): Flow<List<ArticleEntity>>

    @Update
    suspend fun updateArticle(articleEntity: ArticleEntity)
}