package com.example.midterm.datagrid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.midterm.database.Path
import com.example.midterm.database.PathDatabaseDao

class PathDetailViewModel(
        private val pathKey: Long = 0L,
        dataSource: PathDatabaseDao) : ViewModel() {

    val database = dataSource

    private val path: LiveData<Path>

    fun getPath() = path
//    val getPathFrom: String
//        get() = path.value!!.source
//    val getPathTo: String
//        get() = path.value!!.destination
    init {
        path= database.getOnePath(pathKey)
    }

    private val _navigateToViewPath = MutableLiveData<Boolean?>()
    val navigateToViewPath: LiveData<Boolean?>
        get() = _navigateToViewPath

    fun doneNavigating() {
        _navigateToViewPath.value = null
    }

    fun onClose() {
        _navigateToViewPath.value = true
    }

}