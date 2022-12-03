package com.example.midterm.datagrid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.midterm.MapResponse
import com.example.midterm.MapService
import com.example.midterm.database.Path
import com.example.midterm.database.PathDatabaseDao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PathDetailViewModel(
        private val pathKey: Long = 0L,
        dataSource: PathDatabaseDao) : ViewModel() {

    val database = dataSource

    private val path: LiveData<Path>

    fun getPath() = path
    init {
        path = database.getOnePath(pathKey)
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