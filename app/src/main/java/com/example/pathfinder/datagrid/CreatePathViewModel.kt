package com.example.pathfinder.datagrid

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pathfinder.database.PathDatabaseDao
import com.example.pathfinder.database.Path
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreatePathViewModel(val database: PathDatabaseDao,
                          application: Application) : AndroidViewModel(application) {

    var _error = MutableLiveData<String>()
    var _success = MutableLiveData<String>()

    val inputTitle = MutableLiveData<String>()
    val inputSource = MutableLiveData<String>()
    val inputDestination = MutableLiveData<String>()
    val inputDesc = MutableLiveData<String>()

    val _navigateToViewPath = MutableLiveData<Boolean>()

    val navigateToViewPath: LiveData<Boolean>
        get() = _navigateToViewPath

    @SuppressLint("NullSafeMutableLiveData")
    fun doneNavigating() {
        _navigateToViewPath.postValue(null)
    }

    fun createPathButton() {
        if (inputTitle.value == null ||
            inputSource.value == null ||
            inputDestination.value == null ||
            inputDesc.value == null) {
            _error.value = "One or more of the input fields are empty."
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                    val title = inputTitle.value!!
                    val source = inputSource.value!!
                    val destination = inputDestination.value!!
                    val desc = inputDesc.value!!
                    database.insert(Path(0, title, source, destination, desc))
                    inputTitle.postValue("")
                    inputSource.postValue("")
                    inputDestination.postValue("")
                    inputDesc.postValue("")
                    _success.postValue("Path entered successfully!")
                    _navigateToViewPath.postValue(true)
            }
        }
    }

}