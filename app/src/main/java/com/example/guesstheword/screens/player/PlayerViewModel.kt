package com.example.guesstheword.screens.player

import android.util.Patterns
import androidx.lifecycle.*
import com.example.guesstheword.Event
import com.example.guesstheword.database.Player
import kotlinx.coroutines.launch

class PlayerViewModel(private val repository: PlayerRepository) : ViewModel() {

    private var isUpdateOrDelete = false
    private lateinit var playerToUpdateOrDelete: Player

    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()
    val inputUsername = MutableLiveData<String>()
    val inputPassword = MutableLiveData<String>()

    val _savedPlayers: LiveData<List<Player>> = repository.players

    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllOrDeleteButtonText = MutableLiveData<String>()
    private val statusMessage = MutableLiveData<Event<String>>()

    val message: LiveData<Event<String>>
        get() = statusMessage

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate() {
        if (inputName.value == null) {
            statusMessage.value = Event("Please enter player's name")
        } else if (inputEmail.value == null) {
            statusMessage.value = Event("Please enter player's email")
        } else if (inputUsername.value == null) {
            statusMessage.value = Event("Please enter a username")
        } else if (inputUsername.value == null) {
            statusMessage.value = Event("Please enter a password")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()) {
            statusMessage.value = Event("Please enter a correct email address")
        } else {
            if (isUpdateOrDelete) {
                playerToUpdateOrDelete.P_Name = inputName.value!!
                playerToUpdateOrDelete.P_Email = inputEmail.value!!
                updatePlayer(playerToUpdateOrDelete)
            }else {
            val name = inputName.value!!
            val email = inputEmail.value!!
            val username = inputUsername.value!!
            val password = inputPassword.value!!
            insertPlayer(Player(0, name, email, username, password))
            inputName.value = ""
            inputEmail.value = ""
            inputUsername.value = ""
            inputPassword.value = ""
        }}
    }

    private fun insertPlayer(player: Player) = viewModelScope.launch {
        val newRowId = repository.insert(player)
        if (newRowId > -1) {
            statusMessage.value = Event("Player Inserted Successfully $newRowId")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    private fun clearAll() = viewModelScope.launch {
        val noOfRowsDeleted = repository.deleteAll()
        if (noOfRowsDeleted > 0) {
            statusMessage.value = Event("$noOfRowsDeleted Players Deleted Successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }
    fun clearAllOrDelete() {

            if (isUpdateOrDelete) {
                deletePlayer(playerToUpdateOrDelete)
            } else {
                clearAll()}

    }

    fun initUpdateAndDelete(player:Player) {
        inputName.value = player.P_Name
        inputEmail.value = player.P_Email
        inputUsername.value=player.P_Username
        inputPassword.value=player.P_Password
        isUpdateOrDelete = true
        playerToUpdateOrDelete = player
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

    // Any coroutine launched in this scope is automatically canceled if the ViewModel is cleared
    private fun updatePlayer(player: Player) = viewModelScope.launch {
        val noOfRows = repository.update(player)
        if (noOfRows > 0) {
            inputName.value = ""
            inputEmail.value = ""
            inputUsername.value = ""
            inputPassword.value = ""
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("$noOfRows Row Updated Successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }}

        // Any coroutine launched in this scope is automatically canceled if the ViewModel is cleared
        private fun deletePlayer(player: Player) = viewModelScope.launch {
            val noOfRowsDeleted = repository.delete(player)
            if (noOfRowsDeleted > 0) {
                inputName.value = ""
                inputEmail.value = ""
                inputUsername.value = ""
                inputPassword.value = ""
                isUpdateOrDelete = false
                saveOrUpdateButtonText.value = "Save"
                clearAllOrDeleteButtonText.value = "Clear All"
                statusMessage.value = Event("$noOfRowsDeleted Row Deleted Successfully")
            } else {
                statusMessage.value = Event("Error Occurred")
            }
        }

//    fun getSavedPlayers():LiveData<List<Player>> = liveData {
//        repository.players.collect {
//            emit(it)
//        }
}


