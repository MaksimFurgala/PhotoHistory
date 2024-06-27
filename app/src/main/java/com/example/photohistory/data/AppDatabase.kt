package com.example.photohistory.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.photohistory.data.db.models.HistoryPhotoDbModel
import com.example.photohistory.data.db.models.HistoryPhotoRef
import com.example.photohistory.data.db.models.PhotoDbModel

@Database(
    entities = [HistoryPhotoDbModel::class, HistoryPhotoRef::class, PhotoDbModel::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photoHistoryDao(): PhotoHistoryDao
    companion object {
        private var INSTANSE: AppDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "photo_history.db"

        fun getInstance(application: Application): AppDatabase {
            INSTANSE?.let {
                return it
            }

            //TODO
            synchronized(LOCK) {
                INSTANSE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANSE = db
                return db
            }
        }
    }
}