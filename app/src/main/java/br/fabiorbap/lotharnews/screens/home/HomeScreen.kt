package br.fabiorbap.lotharnews.screens.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.fabiorbap.lotharnews.R
import br.fabiorbap.lotharnews.common.util.formatIsoDate
import br.fabiorbap.lotharnews.screens.common.component.CardWithImageAndDescription
import br.fabiorbap.lotharnews.screens.common.component.IconToggle
import br.fabiorbap.lotharnews.screens.common.component.ListHeader
import br.fabiorbap.lotharnews.screens.common.theme.Dimensions
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = koinViewModel()) {

    val state: HomeState by homeViewModel.uiState.collectAsStateWithLifecycle()
    when {
        state.isLoading -> {}
        state.news != null -> {
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
                    ListHeader(R.drawable.ic_trending, stringResource(R.string.home_list_header))
                }

                items(state.news?.articles ?: listOf()) {
                    CardWithImageAndDescription(
                        image = it.urlToImage, description = it.title ?: "",
                        caption = it.publishedAt?.formatIsoDate() ?: "",
                        icons = IconToggle(R.drawable.ic_bookmark_saved, R.drawable.ic_bookmark),
                    )
                }
            }
        }

        state.error != null -> {
            Log.d("Error loading news", "${state.error}")
        }
    }
}