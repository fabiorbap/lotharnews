package br.fabiorbap.lotharnews.screens.main.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {
    @Serializable
    data object Home: Route()
    @Serializable
    data object Profile: Route()
}