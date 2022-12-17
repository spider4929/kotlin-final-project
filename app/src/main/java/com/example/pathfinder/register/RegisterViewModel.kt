package com.example.pathfinder.register

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pathfinder.database.User
import com.example.pathfinder.database.UserDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(val database: UserDatabaseDao,
                        application: Application
) : AndroidViewModel(application) {

    var _error = MutableLiveData<String>()
    var _success = MutableLiveData<String>()

    val inputName = MutableLiveData<String>()

    val inputUsername = MutableLiveData<String>()

    val inputPassword = MutableLiveData<String>()

    val _navigateToLogin = MutableLiveData<Boolean>()

    val navigateToLogin: LiveData<Boolean>
        get() = _navigateToLogin

    @SuppressLint("NullSafeMutableLiveData")
    fun doneNavigating() {
        _navigateToLogin.postValue(null)
    }

    fun registerButton() {
        if (inputName.value == null || inputUsername.value == null || inputPassword.value == null) {
            _error.value = "One or more of the input fields are empty."
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                val usersNames = database.login(inputUsername.value!!)
                if (usersNames != null) {
                    _error.postValue("The username has already been taken. Please choose another one.")
                } else {
                    val name = inputName.value!!
                    val username = inputUsername.value!!
                    val password = inputPassword.value!!
                    database.register(User(0, name, username, password))
                    inputName.postValue("")
                    inputUsername.postValue("")
                    inputPassword.postValue("")
                    _success.postValue("Account registered successfully!")
                    _navigateToLogin.postValue(true)
                }

            }
        }

    }

}
