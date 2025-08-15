package br.fabiorbap.lotharnews.screens.favorites

import androidx.lifecycle.ViewModel
import br.fabiorbap.lotharnews.user.ObserveFavoritesUseCase
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class FavoritesViewModel(val observeFavoritesUseCase: ObserveFavoritesUseCase):
    ViewModel() {



}