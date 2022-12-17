package com.example.pathfinder.login

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pathfinder.database.UserDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(val database: UserDatabaseDao,
                     application: Application
) : AndroidViewModel(application) {

    var _error = MutableLiveData<String>()
    var _success = MutableLiveData<String>()

    val inputUsername = MutableLiveData<String>()

    val inputPassword = MutableLiveData<String>()

    private val _navigateToHome = MutableLiveData<Boolean>()

    val navigateToHome: LiveData<Boolean>
        get() = _navigateToHome

    @SuppressLint("NullSafeMutableLiveData")
    fun doneNavigating() {
        _navigateToHome.postValue(null)
    }

    fun loginButton() {
        if (inputUsername.value == null || inputPassword.value == null) {
            _error.value = "One or more of the input fields are empty."
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                val usersNames = database.login(inputUsername.value!!)
                if (usersNames != null) {
                    if(usersNames.password == inputPassword.value) {
                        val username = inputUsername.value
                        _success.postValue("Logged in successfully! Welcome, $username.")
                        inputUsername.postValue("")
                        inputPassword.postValue("")
                        _navigateToHome.postValue(true)
                    } else {
                        _error.postValue("This password is incorrect. Please try again.")
                    }
                } else {
                    _error.postValue("This username does not exist. Please try again.")
                }

            }
        }
    }


}