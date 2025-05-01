package br.fabiorbap.lotharnews.screens.main.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavDestination
import br.fabiorbap.lotharnews.screens.common.isSelected

@Composable
fun BottomNav(onClick: (route: Route) -> Unit, currentDestination: NavDestination?) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    ) {
        val selectedColor = MaterialTheme.colorScheme.primary
        val unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant
        bottomNavItems.forEachIndexed { _, item ->
            NavigationBarItem(
                selected = currentDestination?.isSelected(item.route) ?: false,
                onClick = {
                    onClick(item.route)
                },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(item.icon),
                        contentDescription = item.name
                    )
                },
                label = {
                    Text(
                        item.name
                    )
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = selectedColor,
                    unselectedIconColor = unselectedColor,
                    disabledIconColor = unselectedColor,
                    selectedTextColor = selectedColor,
                    unselectedTextColor = unselectedColor,
                    disabledTextColor = unselectedColor,
                    selectedIndicatorColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}