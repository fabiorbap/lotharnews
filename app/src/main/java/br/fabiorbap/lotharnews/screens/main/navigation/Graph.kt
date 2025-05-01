package br.fabiorbap.lotharnews.screens.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import br.fabiorbap.lotharnews.screens.home.HomeScreen
import br.fabiorbap.lotharnews.screens.main.navigation.Route.Home
import br.fabiorbap.lotharnews.screens.main.navigation.Route.Profile
import br.fabiorbap.lotharnews.screens.profile.ProfileScreen

fun getGraph(navController: NavController): NavGraph {
    return navController.createGraph(startDestination = Home) {
        composable<Home> {
            HomeScreen()
        }
        composable<Profile> {
            ProfileScreen()
        }
    }
}