package br.fabiorbap.lotharnews.screens.favorites

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.fabiorbap.lotharnews.screens.common.ArticlesList
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoritesScreen(favoritesViewModel: FavoritesViewModel = koinViewModel(), onCardClick: (String) -> Unit) {

    val state: FavoritesState by favoritesViewModel.uiState.collectAsStateWithLifecycle()
    val onFavoriteIconClick = { id: String ->
        favoritesViewModel.handleIntent(FavoritesIntent.RemoveFavorite(id))
    }

    ArticlesList(state.articles, onCardClick = { id -> onCardClick(id) },
        onCardIconClick = onFavoriteIconClick, shouldHideHeader = true)
}