package br.fabiorbap.lotharnews.screens.common.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import br.fabiorbap.lotharnews.R
import br.fabiorbap.lotharnews.screens.common.theme.Dimensions
import coil3.compose.AsyncImage

@Composable
fun CardWithImageAndDescription(
    modifier: Modifier = Modifier, image: String? = null, description: String,
    caption: String
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        if (image != null) AsyncImage(
            modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxSize(),
            contentScale = ContentScale.FillBounds,
            model = image, contentDescription = stringResource(R.string.cd_card_with_image_image),
        )
        Column(modifier = modifier.padding(Dimensions.DefaultSpacing.medium)) {
            Text(
                modifier = modifier
                    .wrapContentHeight()
                    .wrapContentWidth(),
                style = MaterialTheme.typography.titleMedium,
                text = description
            )
            Text(
                modifier = modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .padding(top = Dimensions.DefaultSpacing.small),
                style = MaterialTheme.typography.labelSmall, text = caption
            )
        }
    }
}