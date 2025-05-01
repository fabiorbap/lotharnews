package br.fabiorbap.lotharnews.screens.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import br.fabiorbap.lotharnews.screens.main.navigation.BottomNav
import br.fabiorbap.lotharnews.screens.main.navigation.Route
import br.fabiorbap.lotharnews.screens.main.navigation.getGraph
import org.koin.compose.KoinApplication

@Composable
fun App() {
    KoinApplication(application = {}) {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomNav(
                    onClick = { route -> onBottomNavItemClick(route = route, navController) },
                    currentDestination = currentDestination
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                graph = getGraph(navController),
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