package br.fabiorbap.lotharnews.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.fabiorbap.lotharnews.R
import br.fabiorbap.lotharnews.screens.common.component.MenuItem

@Composable
fun ProfileScreen(onFavoriteClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(Modifier.fillMaxHeight(0.2f))
        Image(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clip(CircleShape)
                .size(100.dp),
            painter = painterResource(R.drawable.ic_profile_placeholder),
            contentDescription = stringResource(R.string.cd_profile_placeholder_image),
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp),
            style = MaterialTheme.typography.titleLarge,
            text = "John Doe",
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.fillMaxHeight(0.1f))
        MenuItem(
            text = stringResource(R.string.profile_favorites_text),
            icon = R.drawable.ic_bookmark_saved,
            onMenuItemClick = { onFavoriteClick() }
        )
    }
}