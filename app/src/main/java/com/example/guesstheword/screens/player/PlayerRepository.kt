package com.example.guesstheword.screens.player

import com.example.guesstheword.database.Player
import com.example.guesstheword.database.PlayerDBDao


class PlayerRepository(private val dao: PlayerDBDao) {

    val players = dao.getAllPlayers()

    suspend fun insert(player: Player): Long {
        return dao.insertPlayer(player)
    }

    suspend fun update(player: Player): Int {
        return dao.updatePlayer(player)
    }

    suspend fun delete(player: Player): Int {
        return dao.deletePlayer(player)
    }

    suspend fun deleteAll(): Int {
        return dao.deleteAll()
    }
}
