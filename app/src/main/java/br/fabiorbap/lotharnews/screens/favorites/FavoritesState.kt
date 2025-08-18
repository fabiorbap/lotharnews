package br.fabiorbap.lotharnews.screens.favorites

import br.fabiorbap.lotharnews.article.model.Article

data class FavoritesState(val articles: List<Article>? = null)