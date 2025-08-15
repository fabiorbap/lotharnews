package br.fabiorbap.lotharnews.screens.common.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun MenuItem(text: String, @DrawableRes icon: Int) {
    val menuItemHorizontalSpacing = 16.dp
    Row {
        Icon(
            modifier = Modifier.padding(start = menuItemHorizontalSpacing,
                end = 8.dp).size(28.dp),
            painter = painterResource(icon), contentDescription = text,
        )
        Text(modifier = Modifier.padding(end = menuItemHorizontalSpacing),
            text = text, style = MaterialTheme.typography.titleMedium)
    }
}