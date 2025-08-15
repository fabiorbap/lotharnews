package br.fabiorbap.lotharnews.screens.home

import br.fabiorbap.lotharnews.article.model.Article
import br.fabiorbap.lotharnews.common.network.response.Error

data class HomeState(
    val isLoading: Boolean = false,
    val articles: List<Article>? = null,
    val error: Error? = null
)