package com.example.guesstheword.screens.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guesstheword.Event
import com.example.guesstheword.screens.player.PlayerRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: PlayerRepository) : ViewModel() {
    val inputEmail = MutableLiveData<String>()
    val inputPassword = MutableLiveData<String>()

    private val _navigatetoGame = MutableLiveData<Boolean>()
    val navigatetoGame: LiveData<Boolean>
        get() = _navigatetoGame

    private val statusMessage = MutableLiveData<Event<String>>()

    val message: LiveData<Event<String>>
        get() = statusMessage

    fun save() {
        if (inputEmail.value == null) {
            statusMessage.value = Event("Please enter an email")
        } else if (inputPassword.value == null) {
            statusMessage.value = Event("Please enter a password")
        } else {
            viewModelScope.launch {
                if ((repository.validateEmail(inputEmail.value!!)) != null) {
                    if ((repository.count(inputEmail.value!!, inputPassword.value!!)) > 0) {
                        statusMessage.value = Event("Login successful!")
                        _navigatetoGame.value = true
                    } else statusMessage.value = Event("Email and password do not match")
                } else {
                    statusMessage.value = Event("Email doesn't exist!")
                }
            }
        }
    }
}