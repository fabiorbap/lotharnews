package br.fabiorbap.lotharnews.screens.main.navigation.appbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import br.fabiorbap.lotharnews.R
import br.fabiorbap.lotharnews.screens.common.theme.Dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(appBarState: AppBarState, onBackButtonClick: () -> Unit) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            AppBarText(appBarState)
        },
        navigationIcon = {
            AppBarNavigationIcon(appBarState) { onBackButtonClick() }
        }
    )
}

@Composable
private fun AppBarNavigationIcon(appBarState: AppBarState, onBackButtonClick: () -> Unit) {
    val backArrowScreens = listOf(AppBarState.Detail, AppBarState.Favorites)
    when {
        appBarState is AppBarState.Home -> Image(
            modifier = Modifier
                .padding(Dimensions.TopAppBar.iconPadding)
                .width(Dimensions.TopAppBar.iconDimension)
                .height(Dimensions.TopAppBar.iconDimension),
            painter = painterResource(R.drawable.ic_launcher),
            contentDescription =
                stringResource(R.string.cd_app_logo)
        )
        backArrowScreens.contains(appBarState) -> IconButton(onClick = { onBackButtonClick() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.cd_back_button)
            )
        }
        appBarState is AppBarState.Profile -> {}
    }
}

@Composable
private fun AppBarText(appBarState: AppBarState) {
    Text(
        text = when (appBarState) {
            AppBarState.Detail -> ""
            AppBarState.Home -> stringResource(R.string.app_name)
            AppBarState.Profile -> stringResource(R.string.bottom_nav_profile)
            AppBarState.Favorites -> ""
        }
    )
}