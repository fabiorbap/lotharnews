package br.fabiorbap.lotharnews.user.usecase

import br.fabiorbap.lotharnews.article.model.Article
import br.fabiorbap.lotharnews.article.model.toModels
import br.fabiorbap.lotharnews.user.model.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

@Factory
data class ObserveFavoritesUseCase(val userRepository: UserRepository) {

    operator fun invoke(): Flow<List<Article>> {
        return userRepository.observeFavorites().map { it.toModels() }
    }
}