package com.example.pathfinder.datagrid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pathfinder.database.Path
import com.example.pathfinder.database.PathDatabaseDao

class PathDetailViewModel(
    private val pathKey: Long = 0L,
    dataSource: PathDatabaseDao) : ViewModel() {

    val database = dataSource

    private val path: LiveData<Path>

    fun getPathKey() = pathKey
    fun getPath() = path
    init {
        path= database.getOnePath(pathKey)
    }

    private val _navigateToViewPath = MutableLiveData<Boolean?>()
    val navigateToViewPath: LiveData<Boolean?>
        get() = _navigateToViewPath

    private val _navigateToEditPath = MutableLiveData<Boolean?>()
    val navigateToEditPath: LiveData<Boolean?>
        get() = _navigateToEditPath

    fun doneNavigating() {
        _navigateToViewPath.value = null
        _navigateToEditPath.value = null
    }

    fun onClose() {
        _navigateToViewPath.value = true
    }
    fun onEdit() {
        _navigateToEditPath.value = true
    }
}