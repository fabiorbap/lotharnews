package br.fabiorbap.lotharnews.screens.common.component.placeholder

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import br.fabiorbap.lotharnews.R

@Composable
fun PlaceholderGenericError(onRetry: () -> Unit) {
    Placeholder(
        text = stringResource(R.string.placeholder_generic_error),
        icon = R.drawable.ic_error) { onRetry() }
}