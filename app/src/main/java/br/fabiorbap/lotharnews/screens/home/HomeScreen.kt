package br.fabiorbap.lotharnews.screens.home

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.fabiorbap.lotharnews.R
import br.fabiorbap.lotharnews.common.util.Error
import br.fabiorbap.lotharnews.screens.common.ArticlesList
import br.fabiorbap.lotharnews.screens.common.component.LoadingFullscreen
import br.fabiorbap.lotharnews.screens.common.component.placeholder.Placeholder
import br.fabiorbap.lotharnews.screens.common.component.placeholder.PlaceholderConnectionError
import br.fabiorbap.lotharnews.screens.common.component.placeholder.PlaceholderGenericError
import kotlinx.coroutines.CoroutineScope
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = koinViewModel(),
    snackbarHostState: SnackbarHostState,
    onCardClick: (String) -> Unit
) {

    val state: HomeState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val onRetry = {
        homeViewModel.handleIntent(
            HomeIntent.GetArticles
        )
    }
    val onFavoriteIconClick: (String) -> Unit = { id ->
        homeViewModel.handleIntent(HomeIntent.FavoriteIconClicked(id))
    }

    when {
        state.isLoading -> LoadingFullscreen()
        state.articles?.isNotEmpty() == true -> ArticlesList(
            state.articles,
            onCardClick,
            onFavoriteIconClick
        )

        state.articles?.isEmpty() == true -> Placeholder(
            stringResource(R.string.home_empty_list_text),
            buttonText = stringResource(R.string.refresh)
        ) { onRetry() }

        state.error != null -> Error(snackbarHostState, scope, onRetry)
    }
}

@Composable
private fun Error(
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    onRetry: () -> Unit
) {
    Error(
        snackbarHostState = snackbarHostState, context = LocalContext.current,
        scope = scope,
        onConnectionError = {
            PlaceholderConnectionError {
                onRetry()
            }
        },
        onError = {
            PlaceholderGenericError {
                onRetry()
            }
        }
    )
}