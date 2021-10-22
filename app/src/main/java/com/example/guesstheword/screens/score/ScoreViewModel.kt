package com.example.guesstheword.screens.score

import android.app.Application
import androidx.lifecycle.*
import com.example.guesstheword.database.Player
import com.example.guesstheword.database.Score
import kotlinx.coroutines.launch

/**
 * ViewModel for the final screen showing the score
 */
class ScoreViewModel(
    finalScore: Int,
    private val repository: ScoreRepository,
    application: Application
) : AndroidViewModel(application) {

    val _savedScore: LiveData<List<Score>> = repository.scores

    private val _eventPlayAgain = MutableLiveData<Boolean>()
    val eventPlayAgain: LiveData<Boolean>
        get() = _eventPlayAgain

    private val _won = MutableLiveData<Boolean>()
    val won: LiveData<Boolean>
        get() = _won


    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    init {
        _score.value = finalScore
        save()

    }

    fun onPlayAgain() {
        _eventPlayAgain.value = true
    }

    fun onPlayAgainComplete() {

        _eventPlayAgain.value = false
    }

    private fun save() = viewModelScope.launch {
        val newScore = Score(lastUpdate = "")
        newScore.score = _score.value!!
        insertScore(newScore)
        _won.value = _score.value!!>0
    }

    private fun insertScore(score: Score) = viewModelScope.launch {
        repository.insert(score)
    }
}

