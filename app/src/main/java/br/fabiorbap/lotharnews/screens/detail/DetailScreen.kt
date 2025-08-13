package br.fabiorbap.lotharnews.screens.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.fabiorbap.lotharnews.R
import br.fabiorbap.lotharnews.common.util.formatIsoDate
import br.fabiorbap.lotharnews.screens.common.component.ContentText
import br.fabiorbap.lotharnews.screens.common.component.DescriptionText
import br.fabiorbap.lotharnews.screens.common.component.LabelText
import br.fabiorbap.lotharnews.screens.common.component.SubtitleText
import br.fabiorbap.lotharnews.screens.common.component.TitleText
import br.fabiorbap.lotharnews.screens.common.theme.Dimensions
import coil3.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun DetailScreen(
    id: String,
    detailViewModel: DetailViewModel = koinViewModel(parameters = { parametersOf(id) })
) {

    val state: DetailState by detailViewModel.uiState.collectAsStateWithLifecycle()
    Column {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth(),
            alignment = Alignment.TopCenter,
            contentScale = ContentScale.FillWidth,
            model = state.article?.urlToImage,
            contentDescription = stringResource(R.string.cd_detail_image)
        )
        Column(modifier = Modifier.padding(horizontal = Dimensions.DefaultSpacing.medium)) {
            TitleText(state.article?.title ?: "")
            SubtitleText(state.article?.publishedAt?.formatIsoDate() ?: "")
            LabelText(
                text = stringResource(
                    R.string.detail_source_text, state.article?.source?.name ?: "",
                    state.article?.author ?: ""
                )
            )
            DescriptionText(state.article?.description ?: "")
            ContentText(state.article?.content ?: "")
        }
    }
}