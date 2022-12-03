package com.example.midterm.datagrid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.midterm.database.PathDatabaseDao

class PathDetailViewModelFactory(
    private val pathKey: Long,
    private val dataSource: PathDatabaseDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PathDetailViewModel::class.java)) {
            return PathDetailViewModel(pathKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}