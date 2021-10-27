package com.example.guesstheword.screens.score

import com.example.guesstheword.database.GameDBDao
import com.example.guesstheword.database.Score

class ScoreRepository(private val dao: GameDBDao) {
    val scores = dao.getAllScores()

    suspend fun insert(score: Score): Long {
        return dao.insertScore(score)
    }
}