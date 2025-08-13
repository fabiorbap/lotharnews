package br.fabiorbap.lotharnews.article.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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
        article.url
        FROM ArticleEntity article WHERE article.url = :id"""
    )
    suspend fun getArticle(id: String): ArticleEntity

}