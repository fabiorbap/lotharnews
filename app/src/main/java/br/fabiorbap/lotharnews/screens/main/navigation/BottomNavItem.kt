package br.fabiorbap.lotharnews.screens.main.navigation

import androidx.annotation.DrawableRes
import br.fabiorbap.lotharnews.R
import br.fabiorbap.lotharnews.screens.main.navigation.BottomNavItem.Home
import br.fabiorbap.lotharnews.screens.main.navigation.BottomNavItem.Profile


sealed class BottomNavItem(val name: String, @DrawableRes val icon: Int, val route: Route) {

    data object Home: BottomNavItem("Home", R.drawable.ic_home, Route.Home)
    data object Profile: BottomNavItem("Profile", R.drawable.ic_profile, Route.Profile)
}

val bottomNavItems = listOf(Home, Profile)
