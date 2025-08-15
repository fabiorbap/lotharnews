package br.fabiorbap.lotharnews.user

import br.fabiorbap.lotharnews.article.model.ArticleDao
import br.fabiorbap.lotharnews.article.model.ArticleEntity
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
data class ObserveFavoritesUseCase(val userRepository: UserRepository) {

    operator fun invoke(): Flow<List<ArticleEntity>> {
        return userRepository.observeFavorites()
    }
}