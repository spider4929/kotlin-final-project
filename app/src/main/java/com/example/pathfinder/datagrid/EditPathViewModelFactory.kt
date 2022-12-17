package com.example.pathfinder.datagrid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pathfinder.database.PathDatabaseDao

class EditPathViewModelFactory (
    private val pathKey: Long,
    private val dataSource: PathDatabaseDao
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EditPathViewModel::class.java)) {
                return EditPathViewModel(pathKey, dataSource) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}