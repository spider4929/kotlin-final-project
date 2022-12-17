package com.example.pathfinder.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PathDatabaseDao {

    @Insert
    fun insert(path: Path)

    @Query("SELECT * FROM path_table WHERE pathId = :key")
    fun getOnePath(key: Long): LiveData<Path>

    @Query("SELECT * FROM path_table ORDER BY pathId DESC")
    fun getAllPath(): LiveData<List<Path>>

    @Query("SELECT source FROM path_table WHERE pathID = :key")
    fun getSource(key: Long): String

    @Query("SELECT destination FROM path_table WHERE pathId = :key ")
    fun getDestination(key: Long): String

    @Query("DELETE FROM path_table")
    suspend fun clear()

    @Query("DELETE FROM path_table WHERE pathId = :key")
    suspend fun deleteOne(key: Long)

    @Query("UPDATE path_table SET title = :title, source = :source, destination = :destination, description = :description WHERE pathId = :key")
    fun update(key: Long, title: String, source: String, destination: String, description: String)
}