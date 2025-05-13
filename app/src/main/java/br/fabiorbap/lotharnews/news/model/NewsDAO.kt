package br.fabiorbap.lotharnews.news.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.annotation.Single

@Single
class NewsDAO {
    private val _news = MutableStateFlow<News?>(null)
    private val news: StateFlow<News?> = _news

    fun setNews(news: News) {
        this._news.update { news }
    }

    fun observeNews(): Flow<News?> {
        return news
    }

}