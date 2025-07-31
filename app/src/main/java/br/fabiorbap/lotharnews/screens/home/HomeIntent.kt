package br.fabiorbap.lotharnews.screens.home

sealed class HomeIntent {
    data object GetNews: HomeIntent()
}