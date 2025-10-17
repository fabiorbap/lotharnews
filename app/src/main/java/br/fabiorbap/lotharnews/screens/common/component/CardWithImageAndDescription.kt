package br.fabiorbap.lotharnews.screens.common.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.fabiorbap.lotharnews.R
import br.fabiorbap.lotharnews.screens.common.theme.Dimensions
import coil3.compose.AsyncImage

data class IconToggle(@DrawableRes val activateIcon: Int, @DrawableRes val inactivateIcon: Int)

@Composable
fun CardWithImageAndDescription(
    modifier: Modifier = Modifier, image: String? = null, description: String,
    caption: String, icons: IconToggle? = null, onContentClick: () -> Unit = {},
    onIconClick: () -> Unit = {},
    isIconActive: Boolean = false
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = onContentClick
    ) {
        if (image != null) ImageBlock(
            modifier,
            image,
            if (isIconActive) icons?.activateIcon else icons?.inactivateIcon
        ) {
            onIconClick()
        }
        TextBlock(modifier, description, caption)
    }
}

@Composable
private fun ImageBlock(
    modifier: Modifier, image: String?, @DrawableRes icon: Int? = null,
    onIconClick: () -> Unit
) {
    Column {
        Box(
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.FillBounds,
                model = image,
                contentDescription = stringResource(R.string.cd_card_with_image_image)
            )
            if (icon != null) Icon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(
                        top = Dimensions.DefaultSpacing.medium,
                        end = Dimensions.DefaultSpacing.medium
                    )
                    .height(28.dp)
                    .width(28.dp)
                    .clickable { onIconClick() },
                painter = painterResource(icon),
                contentDescription = stringResource(R.string.cd_card_with_image_icon),
                tint = Color.White,
            )
        }
    }
}

@Composable
private fun TextBlock(modifier: Modifier, description: String, caption: String) {
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