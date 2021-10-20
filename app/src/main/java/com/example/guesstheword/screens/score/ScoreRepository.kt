package com.example.guesstheword.screens.score
import com.example.guesstheword.database.PlayerDBDao
import com.example.guesstheword.database.Score

class ScoreRepository(private val dao:PlayerDBDao) {
    val scores = dao.getAllScores()

    suspend fun insert(score: Score): Long {
        return dao.insertScore(score)
    }
}