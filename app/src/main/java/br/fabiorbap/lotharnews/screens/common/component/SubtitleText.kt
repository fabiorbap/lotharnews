package br.fabiorbap.lotharnews.screens.common.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.fabiorbap.lotharnews.common.util.formatIsoDate

@Composable
fun SubtitleText(text: String) {
    Text(
        modifier = Modifier.padding(vertical = 8.dp),
        style = MaterialTheme.typography.labelLarge,
        text = text,
    )
}