package com.example.pathfinder.datagrid

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pathfinder.database.Path
import com.example.pathfinder.database.PathDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditPathViewModel(private val pathKey: Long = 0L, dataSource: PathDatabaseDao)
    : ViewModel() {

    var _error = MutableLiveData<String>()
    var _success = MutableLiveData<String>()

    val inputTitle = MutableLiveData<String>()
    val inputSource = MutableLiveData<String>()
    val inputDestination = MutableLiveData<String>()
    val inputDesc = MutableLiveData<String>()

    private val path: LiveData<Path>

    val database = dataSource

    fun getPath() = path
    init {
        path= database.getOnePath(pathKey)
    }

    val pathValue = getPath().value
    val _navigateToViewPath = MutableLiveData<Boolean>()

    val navigatetoViewPath: LiveData<Boolean>
        get() = _navigateToViewPath

    @SuppressLint("NullSafeMutableLiveData")
    fun doneNavigating() {
        _navigateToViewPath.postValue(null)
    }
    fun onDelete() {
        CoroutineScope(Dispatchers.IO).launch {
            database.deleteOne(pathKey)
            _success.postValue("Path deleted successfully!")
            _navigateToViewPath.postValue(true)
        }
    }
    fun onClose() {
        _navigateToViewPath.postValue(true)
    }
    fun editPathButton() {
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
                database.update(pathKey, title, source, destination, desc)
                inputTitle.postValue("")
                inputSource.postValue("")
                inputDestination.postValue("")
                inputDesc.postValue("")
                _success.postValue("Path edited successfully!")
                _navigateToViewPath.postValue(true)
            }
        }
    }
}