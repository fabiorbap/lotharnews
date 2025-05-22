package br.fabiorbap.lotharnews.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import br.fabiorbap.lotharnews.screens.home.HomeScreen
import br.fabiorbap.lotharnews.screens.main.navigation.appbar.AppBar
import br.fabiorbap.lotharnews.screens.main.navigation.appbar.AppBarState
import br.fabiorbap.lotharnews.screens.main.navigation.bottomnav.BottomNav
import br.fabiorbap.lotharnews.screens.main.navigation.Route
import br.fabiorbap.lotharnews.screens.main.navigation.Route.Home
import br.fabiorbap.lotharnews.screens.main.navigation.Route.Profile
import br.fabiorbap.lotharnews.screens.profile.ProfileScreen
import org.koin.compose.KoinContext

@Composable
fun App() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    KoinContext {

        var appBarState: AppBarState by remember { mutableStateOf(AppBarState.Home) }

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            bottomBar = {
                BottomNav(
                    onClick = { route -> onBottomNavItemClick(route = route, navController) },
                    currentDestination = currentDestination
                )
            },
            topBar = {
                AppBar(appBarState, { navController.popBackStack() })
            }
        ) { innerPadding ->
            val graph = navController.createGraph(startDestination = Home) {
                composable<Home> {
                    appBarState = AppBarState.Home
                    HomeScreen()
                }
                composable<Profile> {
                    appBarState = AppBarState.Profile
                    ProfileScreen()
                }
            }
            NavHost(
                navController = navController,
                graph = graph,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

fun onBottomNavItemClick(route: Route, navController: NavController) {
    navController.navigate(route) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}