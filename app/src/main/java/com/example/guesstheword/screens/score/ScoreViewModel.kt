package com.example.guesstheword.screens.score

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guesstheword.database.Player
import com.example.guesstheword.database.Score
import kotlinx.coroutines.launch

/**
 * ViewModel for the final screen showing the score
 */

class ScoreViewModel(finalScore: Int, private val repository: ScoreRepository) : ViewModel() {

    val _savedScore: LiveData<List<Score>> = repository.scores

    private val _eventPlayAgain = MutableLiveData<Boolean>()
    val eventPlayAgain: LiveData<Boolean>
        get() = _eventPlayAgain

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    init {
        _score.value = finalScore
    }

    fun onPlayAgain() {
        _eventPlayAgain.value = true
    }

    fun onPlayAgainComplete() {
        _eventPlayAgain.value = false
    }

    fun initUpdateAndDelete(score: Score) {
        _score.value = score.score
    }

    fun save() = viewModelScope.launch {
        insertScore(Score(0, _score.value!!, ""))
    }

    private fun insertScore(score: Score) = viewModelScope.launch {
        repository.insert(score)
    }

    fun getSavedScores() = repository.scores
}

