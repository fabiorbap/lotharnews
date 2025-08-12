package br.fabiorbap.lotharnews.screens.detail

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import br.fabiorbap.lotharnews.R
import coil3.compose.AsyncImage

@Composable
fun DetailScreen(id: String) {
    Log.d("Detail screen", id)
    AsyncImage(
        modifier = Modifier
            .fillMaxSize(),
        contentScale = ContentScale.FillBounds,
        model = image,
        contentDescription = stringResource(R.string.cd_card_with_image_image)
    )
}