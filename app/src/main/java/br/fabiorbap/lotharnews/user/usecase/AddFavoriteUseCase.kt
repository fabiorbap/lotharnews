package br.fabiorbap.lotharnews.user.usecase

import br.fabiorbap.lotharnews.user.model.UserRepository
import org.koin.core.annotation.Factory

@Factory
class AddFavoriteUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(id: String) {
        userRepository.addFavorite(id)
    }
}