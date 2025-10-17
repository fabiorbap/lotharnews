package br.fabiorbap.lotharnews.common.util

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import br.fabiorbap.lotharnews.screens.main.navigation.Route

fun NavDestination.isSelected(route: Route): Boolean = hierarchy.any { it.route == route::class.qualifiedName }