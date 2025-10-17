package br.fabiorbap.lotharnews.common.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import br.fabiorbap.lotharnews.article.model.ArticleDao
import br.fabiorbap.lotharnews.article.model.ArticleEntity

@Database(
    entities = [ArticleEntity::class], version = 2,
    autoMigrations = [AutoMigration(from = 1, to = 2)]
)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}