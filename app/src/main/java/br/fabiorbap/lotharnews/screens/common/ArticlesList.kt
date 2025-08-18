package br.fabiorbap.lotharnews.screens.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import br.fabiorbap.lotharnews.R
import br.fabiorbap.lotharnews.article.model.Article
import br.fabiorbap.lotharnews.common.util.formatIsoDate
import br.fabiorbap.lotharnews.screens.common.component.CardWithImageAndDescription
import br.fabiorbap.lotharnews.screens.common.component.IconToggle
import br.fabiorbap.lotharnews.screens.common.component.ListHeader
import br.fabiorbap.lotharnews.screens.common.theme.Dimensions
import java.util.UUID

@Composable
fun ArticlesList(
    articles: List<Article>?, onCardClick: (String) -> Unit,
    onCardIconClick: (String) -> Unit,
    shouldHideHeader: Boolean = false
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = Dimensions.DefaultSpacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(vertical = Dimensions.DefaultSpacing.small),
        verticalArrangement = Arrangement.spacedBy(Dimensions.DefaultSpacing.medium)
    ) {
        if (!shouldHideHeader) item(key = "header") {
            ListHeader(R.drawable.ic_trending, stringResource(R.string.home_header))
        }

        items(articles ?: listOf(), key = { item -> item.url ?: UUID.randomUUID() }) {
            CardWithImageAndDescription(
                image = it.urlToImage, description = it.title ?: "",
                caption = it.publishedAt?.formatIsoDate() ?: "",
                icons = IconToggle(R.drawable.ic_bookmark_saved, R.drawable.ic_bookmark),
                onContentClick = { onCardClick(it.url ?: "") },
                onIconClick = { onCardIconClick(it.url ?: "") },
                isIconActive = it.isFavorite ?: false
            )
        }
    }
}