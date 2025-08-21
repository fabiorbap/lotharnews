package br.fabiorbap.lotharnews.common.util

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import br.fabiorbap.lotharnews.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Error(
    snackbarHostState: SnackbarHostState,
    context: Context,
    scope: CoroutineScope,
    onConnectionError: @Composable () -> Unit = {
        LaunchedEffect(Unit) {
            scope.launch {
                snackbarHostState.showSnackbar(context.getString(R.string.generic_error_text))
            }
        }
    },
    onError: @Composable () -> Unit = {
        LaunchedEffect(Unit) {
            scope.launch {
                snackbarHostState.showSnackbar(context.getString(R.string.generic_error_text))
            }
        }
    },
) {
    if (!isNetworkAvailable(context)) {
        onConnectionError()
        return
    }
    onError()
}