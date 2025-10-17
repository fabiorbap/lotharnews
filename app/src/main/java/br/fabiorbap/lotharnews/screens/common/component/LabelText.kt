package br.fabiorbap.lotharnews.screens.common.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.fabiorbap.lotharnews.R

@Composable
fun LabelText(text: String) {
    Text(
        modifier = Modifier.padding(bottom = 16.dp),
        style = MaterialTheme.typography.labelMedium,
        text = text
    )
}