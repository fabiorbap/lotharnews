package br.fabiorbap.lotharnews.screens.common.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import br.fabiorbap.lotharnews.R
import br.fabiorbap.lotharnews.screens.common.theme.Dimensions

@Composable
fun ListHeader(@DrawableRes icon: Int, text: String) {
    Row {
        Icon(
            modifier = Modifier.padding(end = Dimensions.DefaultSpacing.xSmall),
            painter = painterResource(icon),
            contentDescription = stringResource(R.string.cd_list_header_icon)
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = text,
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Start
        )
    }
}