package br.fabiorbap.lotharnews.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.fabiorbap.lotharnews.R
import br.fabiorbap.lotharnews.article.model.Article
import br.fabiorbap.lotharnews.common.util.formatIsoDate
import br.fabiorbap.lotharnews.common.util.showError
import br.fabiorbap.lotharnews.screens.common.component.CardWithImageAndDescription
import br.fabiorbap.lotharnews.screens.common.component.IconToggle
import br.fabiorbap.lotharnews.screens.common.component.ListHeader
import br.fabiorbap.lotharnews.screens.common.component.LoadingFullscreen
import br.fabiorbap.lotharnews.screens.common.component.placeholder.Placeholder
import br.fabiorbap.lotharnews.screens.common.component.placeholder.PlaceholderConnectionError
import br.fabiorbap.lotharnews.screens.common.component.placeholder.PlaceholderGenericError
import br.fabiorbap.lotharnews.screens.common.theme.Dimensions
import kotlinx.coroutines.CoroutineScope
import org.koin.androidx.compose.koinViewModel
import java.util.UUID

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
            HomeIntent.GetNews
        )
    }

    when {
        state.isLoading -> LoadingFullscreen()
        state.articles?.isNotEmpty() == true -> NewsList(state.articles, onCardClick)
        state.articles?.isEmpty() == true -> Placeholder(stringResource(R.string.home_empty_list_text),
            buttonText = stringResource(R.string.refresh)) { onRetry() }
        state.error != null -> Error(snackbarHostState, scope, onRetry)
    }
}

@Composable
private fun NewsList(articles: List<Article>?, onCardClick: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = Dimensions.DefaultSpacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(vertical = Dimensions.DefaultSpacing.small),
        verticalArrangement = Arrangement.spacedBy(Dimensions.DefaultSpacing.medium)
    ) {
        item(key = "header") {
            ListHeader(R.drawable.ic_trending, stringResource(R.string.home_header))
        }

        items(articles ?: listOf(), key = { item -> item.url ?: UUID.randomUUID() }) {
            CardWithImageAndDescription(
                image = it.urlToImage, description = it.title ?: "",
                caption = it.publishedAt?.formatIsoDate() ?: "",
                icons = IconToggle(R.drawable.ic_bookmark_saved, R.drawable.ic_bookmark),
                onContentClick = { onCardClick(it.url ?: "") }
            )
        }
    }
}

@Composable
private fun Error(
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    onRetry: () -> Unit
) {
    showError(
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