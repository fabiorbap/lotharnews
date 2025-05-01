package br.fabiorbap.lotharnews.screens.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import br.fabiorbap.lotharnews.screens.main.navigation.BottomNav
import br.fabiorbap.lotharnews.screens.main.navigation.getGraph
import org.koin.compose.KoinApplication

@Composable
fun App() {
    KoinApplication(application = {}) {
        val navController = rememberNavController()
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = { BottomNav { route -> navController.navigate(route) } }) { innerPadding ->
            NavHost(
                navController = navController,
                graph = getGraph(navController),
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}