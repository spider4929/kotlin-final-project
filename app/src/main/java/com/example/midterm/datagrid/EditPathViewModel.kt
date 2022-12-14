package com.example.midterm.datagrid

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.midterm.database.Path
import com.example.midterm.database.PathDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditPathViewModel(private val pathKey: Long = 0L, dataSource: PathDatabaseDao)
    : ViewModel() {


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
    fun setData(){
        if (pathValue != null){
            inputTitle.postValue(pathValue.title)
            inputSource.postValue(pathValue.source)
            inputDestination.postValue(pathValue.destination)
            inputDesc.postValue(pathValue.description)
        }
    }
    val _navigateToViewPath = MutableLiveData<Boolean>()

    val navigatetoViewPath: LiveData<Boolean>
        get() = _navigateToViewPath

    @SuppressLint("NullSafeMutableLiveData")
    fun doneNavigating() {
        _navigateToViewPath.postValue(null)
    }

    fun onClose() {
        _navigateToViewPath.postValue(true)
    }
    fun editPathButton() {
        CoroutineScope(Dispatchers.IO).launch{
            val title = inputTitle.value!!
            val source = inputSource.value!!
            val destination = inputDestination.value!!
            val desc = inputDesc.value!!
            database.update(pathKey, title, source, destination, desc)
            inputTitle.postValue("")
            inputSource.postValue("")
            inputDestination.postValue("")
            inputDesc.postValue("")
            _navigateToViewPath.postValue(true)
        }
    }
}