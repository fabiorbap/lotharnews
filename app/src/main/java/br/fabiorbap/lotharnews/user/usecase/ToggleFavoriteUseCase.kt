package br.fabiorbap.lotharnews.user.usecase

import br.fabiorbap.lotharnews.article.usecase.GetArticleUseCase
import org.koin.core.annotation.Factory

@Factory
class ToggleFavoriteUseCase(private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
    private val getArticleUseCase: GetArticleUseCase) {

    suspend operator fun invoke(id: String) {
        val article = getArticleUseCase(id)
        if (article.isFavorite == true) removeFavoriteUseCase(id)
        else addFavoriteUseCase(id)
    }

}