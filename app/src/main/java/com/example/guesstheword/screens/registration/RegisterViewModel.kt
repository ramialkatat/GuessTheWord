package com.example.guesstheword.screens.registration

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guesstheword.Event
import com.example.guesstheword.database.Player
import com.example.guesstheword.screens.player.PlayerRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: PlayerRepository) : ViewModel() {

    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()
    val inputUsername = MutableLiveData<String>()
    val inputPassword = MutableLiveData<String>()

    val _savedPlayers: LiveData<List<Player>> = repository.players

    private val statusMessage = MutableLiveData<Event<String>>()

    private val _navigatetoLogin = MutableLiveData<Boolean>()
    val navigatetoLogin: LiveData<Boolean>
        get() = _navigatetoLogin

    val message: LiveData<Event<String>>
        get() = statusMessage

    fun save() {
        if (inputName.value == null) {
            statusMessage.value = Event("Please enter player's name")
        } else if (inputEmail.value == null) {
            statusMessage.value = Event("Please enter player's email")
        } else if (inputUsername.value == null) {
            statusMessage.value = Event("Please enter a username")
        } else if (inputPassword.value == null) {
            statusMessage.value = Event("Please enter a password")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()) {
            statusMessage.value = Event("Please enter a correct email address")
        } else {
            viewModelScope.launch {
                if ((repository.getUserName(inputUsername.value!!)) == null) {
                    if ((repository.validateEmail(inputEmail.value!!)) == null) {
//                    if (isUpdateOrDelete) {
//                        playerToUpdateOrDelete.P_Name = inputName.value!!
//                        playerToUpdateOrDelete.P_Email = inputEmail.value!!
//                        updatePlayer(playerToUpdateOrDelete)
//                    } else {
                        val name = inputName.value!!
                        val email = inputEmail.value!!
                        val username = inputUsername.value!!
                        val password = inputPassword.value!!
                        insertPlayer(Player(0, name, email, username, password))
                        inputName.value = ""
                        inputEmail.value = ""
                        inputUsername.value = ""
                        inputPassword.value = ""


                    } else {
                        statusMessage.value =
                            Event("An account is already registered using that email!!")
                    }
                } else {
                    statusMessage.value = Event("Username exists!")
                }
            }
        }
    }

    private fun insertPlayer(player: Player) = viewModelScope.launch {

        val newRowId = repository.insert(player)
        if (newRowId > -1) {
            statusMessage.value = Event("Player #$newRowId Inserted Successfully ")
            _navigatetoLogin.value = true
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

}