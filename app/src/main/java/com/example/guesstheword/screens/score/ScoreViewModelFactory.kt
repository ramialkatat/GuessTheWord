package com.example.guesstheword.screens.score


import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
//Since we need to pass some input data to the constructor of the viewModel , we need to create a factory class for viewModel.
class ScoreViewModelFactory(private val finalScore: Int,private val repository: ScoreRepository,val application:Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScoreViewModel::class.java)) {
            return ScoreViewModel(finalScore,repository,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
