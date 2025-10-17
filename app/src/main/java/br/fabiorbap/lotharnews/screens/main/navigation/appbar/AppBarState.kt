package br.fabiorbap.lotharnews.screens.main.navigation.appbar

sealed class AppBarState {
    data object Home : AppBarState()
    data object Detail : AppBarState()
    data object Favorites : AppBarState()
    data object Profile : AppBarState()
}