package com.example.pathfinder.datagrid;

import android.app.Application;
import androidx.lifecycle.*
import com.example.pathfinder.database.Path
import com.example.pathfinder.database.PathDatabaseDao;
import kotlinx.coroutines.launch

class ViewPathViewModel(
        dataSource: PathDatabaseDao,
        application: Application) : ViewModel() {

    val database = dataSource

    private var path = MutableLiveData<Path?>()

    val paths = database.getAllPath()

    val pathsString = Transformations.map(paths) { paths ->
        formatPath(paths, application.resources)
    }

    val clearButtonVisible = Transformations.map(paths) {
        it?.isNotEmpty()
    }

    private var _showSnackbarEvent = MutableLiveData<Boolean?>()

    val showSnackBarEvent: LiveData<Boolean?>
    get() = _showSnackbarEvent

    private val _navigateToPathDetail = MutableLiveData<Long>()

    val navigateToPathDetail
        get() = _navigateToPathDetail

    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = null
    }
    fun onPathClicked(id: Long) {
        _navigateToPathDetail.value = id
    }
    fun onPathDetailNavigated() {
        _navigateToPathDetail.value = null
    }

    private suspend fun clear() {
        database.clear()
    }

    fun onClear() {
        viewModelScope.launch {
            clear()
            _showSnackbarEvent.value = true
        }
    }
}
