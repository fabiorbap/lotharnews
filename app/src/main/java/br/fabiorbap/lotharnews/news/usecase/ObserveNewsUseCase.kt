package br.fabiorbap.lotharnews.news.usecase

import br.fabiorbap.lotharnews.news.model.News
import br.fabiorbap.lotharnews.news.model.NewsDAO
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class ObserveNewsUseCase(private val newsDAO: NewsDAO) {

    operator fun invoke(): Flow<News?> {
        return newsDAO.observeNews()
    }
}