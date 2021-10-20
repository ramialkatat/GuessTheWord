package com.example.guesstheword.screens.score


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
//Since we need to pass some input data to the constructor of the viewModel , we need to create a factory class for viewModel.
class ScoreViewModelFactory(private val finalScore: Int,private val repository: ScoreRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScoreViewModel::class.java)) {
            return ScoreViewModel(finalScore,repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
