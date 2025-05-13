package br.fabiorbap.lotharnews.screens.home

import br.fabiorbap.lotharnews.common.network.Error
import br.fabiorbap.lotharnews.news.model.News

data class HomeState(
    val isLoading: Boolean = false,
    val news: News? = null,
    val error: Error? = null
)