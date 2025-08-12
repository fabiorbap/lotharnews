package br.fabiorbap.lotharnews.screens.common.component.placeholder

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import br.fabiorbap.lotharnews.R
import br.fabiorbap.lotharnews.screens.common.theme.Dimensions

@Composable
fun Placeholder(
    text: String = stringResource(R.string.placeholder_generic_error),
    @DrawableRes icon: Int = R.drawable.ic_error,
    buttonText: String = stringResource(R.string.retry),
    onRetryClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            painter = painterResource(icon),
            contentDescription = stringResource(R.string.cd_card_with_image_icon),
            tint = MaterialTheme.colorScheme.primary,
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(Dimensions.DefaultSpacing.medium),
            text = text,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
        )
        Button(onClick = onRetryClick) {
            Text(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                text = buttonText
            )
        }
    }
}