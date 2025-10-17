package br.fabiorbap.lotharnews.di

import android.content.Context
import androidx.room.Room
import br.fabiorbap.lotharnews.article.model.ArticleDao
import br.fabiorbap.lotharnews.common.database.ApplicationDatabase
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class DatabaseModule {

    @Single
    fun provideRoomDatabase(applicationContext: Context): ApplicationDatabase {
        return Room.databaseBuilder(
            applicationContext,
            ApplicationDatabase::class.java,
            "lothar-news-database"
        ).build()
    }

    @Single
    fun provideArticleDao(applicationDatabase: ApplicationDatabase): ArticleDao {
        return applicationDatabase.articleDao()
    }

}