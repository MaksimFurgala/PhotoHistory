package com.example.photohistory.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.photohistory.data.db.models.HistoryPhotoDbModel
import com.example.photohistory.data.db.models.HistoryPhotoWithPhotos
import com.example.photohistory.data.db.models.PhotoDbModel

@Dao
interface PhotoHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPhoto(photoDbModel: PhotoDbModel)

    @Delete
    suspend fun deletePhoto(photoDbModel: PhotoDbModel)

    @Query("SELECT * FROM photos WHERE id=:photoId LIMIT 1")
    suspend fun getPhoto(photoId: Int): PhotoDbModel

    @Update
    suspend fun editPhoto(photoDbModel: PhotoDbModel)

    @Query("SELECT * FROM photos")
    fun getPhotoList(): LiveData<List<PhotoDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHistoryPhoto(historyPhotoDbModel: HistoryPhotoDbModel)
}