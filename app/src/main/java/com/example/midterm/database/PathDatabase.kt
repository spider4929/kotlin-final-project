package com.example.midterm.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Path::class], version = 1, exportSchema = false)
abstract class PathDatabase : RoomDatabase() {

    abstract val pathDatabaseDao: PathDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: PathDatabase? = null

        fun getInstance(context: Context): PathDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PathDatabase::class.java,
                        "path_details_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}