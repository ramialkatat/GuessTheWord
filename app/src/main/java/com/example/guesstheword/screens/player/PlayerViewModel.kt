package com.example.guesstheword.screens.player

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.guesstheword.database.Player

class PlayerViewModel(private val repository: PlayerRepository) : ViewModel() {
    val _savedPlayers: LiveData<List<Player>> = repository.players
}


