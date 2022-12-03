package com.example.midterm.datagrid

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.midterm.database.PathDatabaseDao
import com.example.midterm.database.Path
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreatePathViewModel(val database: PathDatabaseDao,
                          application: Application) : AndroidViewModel(application) {

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
        CoroutineScope(Dispatchers.IO).launch {
            val title = inputTitle.value!!
            val source = inputSource.value!!
            val destination = inputDestination.value!!
            val desc = inputDesc.value!!
            database.insert(Path(0,title,source,destination,desc))
            inputTitle.postValue("")
            inputSource.postValue("")
            inputDestination.postValue("")
            inputDesc.postValue("")
            _navigateToViewPath.postValue(true)
        }
    }

}