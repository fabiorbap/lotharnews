package br.fabiorbap.lotharnews.screens.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import br.fabiorbap.lotharnews.R
import br.fabiorbap.lotharnews.common.util.formatIsoDate
import br.fabiorbap.lotharnews.news.model.News
import br.fabiorbap.lotharnews.screens.common.component.CardWithImageAndDescription
import br.fabiorbap.lotharnews.screens.common.component.IconToggle
import br.fabiorbap.lotharnews.screens.common.component.ListHeader
import br.fabiorbap.lotharnews.screens.common.component.Placeholder
import br.fabiorbap.lotharnews.screens.common.theme.Dimensions
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = koinViewModel()) {

    val state: HomeState by homeViewModel.uiState.collectAsStateWithLifecycle()
    when {
        state.isLoading -> Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            CircularProgressIndicator()
        }

        state.news != null -> NewsList(state.news)
        state.error != null -> {
            Placeholder { homeViewModel.handleIntent(HomeIntent.GetNews) }
        }
    }
}

@Composable
private fun NewsList(news: News?) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = Dimensions.DefaultSpacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(vertical = Dimensions.DefaultSpacing.small),
        verticalArrangement = Arrangement.spacedBy(Dimensions.DefaultSpacing.medium)
    ) {
        item {
            ListHeader(R.drawable.ic_trending, stringResource(R.string.home_header))
        }

        items(news?.articles ?: listOf()) {
            CardWithImageAndDescription(
                image = it.urlToImage, description = it.title ?: "",
                caption = it.publishedAt?.formatIsoDate() ?: "",
                icons = IconToggle(R.drawable.ic_bookmark_saved, R.drawable.ic_bookmark),
            )
        }
    }
}