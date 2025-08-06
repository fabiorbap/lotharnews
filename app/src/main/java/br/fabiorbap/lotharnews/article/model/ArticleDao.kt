package br.fabiorbap.lotharnews.article.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<ArticleEntity>)

    @Query(
        """SELECT * FROM ArticleEntity"""
    )
    fun observeArticles(): Flow<List<ArticleEntity>>

}