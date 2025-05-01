package br.fabiorbap.lotharnews.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen() {
    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
        Text(text = "Home", modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
    }
}