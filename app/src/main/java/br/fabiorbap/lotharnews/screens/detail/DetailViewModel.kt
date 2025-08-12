package br.fabiorbap.lotharnews.screens.detail

import androidx.lifecycle.ViewModel
import br.fabiorbap.lotharnews.article.usecase.GetArticleUseCase
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class DetailViewModel(val id: String, val getArticleUseCase: GetArticleUseCase): ViewModel() {

    

}