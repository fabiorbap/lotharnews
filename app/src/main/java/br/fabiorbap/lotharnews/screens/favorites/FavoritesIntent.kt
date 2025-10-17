package br.fabiorbap.lotharnews.screens.favorites

sealed class FavoritesIntent {
    data class RemoveFavorite(val id: String): FavoritesIntent()
}