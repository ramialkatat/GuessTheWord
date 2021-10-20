package com.example.guesstheword.screens.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
//Since we need to pass some input data to the constructor of the viewModel , we need to create a factory class for viewModel.
class PlayerViewModelFactory(
    private val repository: PlayerRepository
):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PlayerViewModel::class.java)){
            return PlayerViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}

//Our ViewModel class has constructor parameters. Therefore, to construct an instance of it we have to get the support of a Factory class.