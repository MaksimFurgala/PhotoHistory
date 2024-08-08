package com.example.photohistory.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.photohistory.data.db.models.HistoryPhotoDbModel
import com.example.photohistory.data.db.models.HistoryPhotoRef
import com.example.photohistory.data.db.models.HistoryPhotoWithPhotos
import com.example.photohistory.data.db.models.PhotoDbModel

@Dao
interface PhotoHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPhoto(photoDbModel: PhotoDbModel): Long

    @Delete
    suspend fun deletePhoto(photoDbModel: PhotoDbModel)

    @Query("SELECT * FROM photos WHERE photoId=:photoId LIMIT 1")
    suspend fun getPhoto(photoId: Int): PhotoDbModel

    @Update
    suspend fun editPhoto(photoDbModel: PhotoDbModel)

    @Query("SELECT * FROM photos")
    fun getPhotoList(): LiveData<List<PhotoDbModel>>

    @Transaction
    @Query("SELECT * FROM history_photos WHERE historyPhotoId=:historyPhotoId")
    fun getPhotoListOfHistoryPhoto(historyPhotoId: Long): LiveData<List<HistoryPhotoWithPhotos>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHistoryPhoto(historyPhotoDbModel: HistoryPhotoDbModel): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHistoryPhotoWithPhoto(historyPhotoRef: HistoryPhotoRef)

    @Transaction
    @Query("SELECT * FROM history_photos")
    fun getHistoryPhotoList(): LiveData<List<HistoryPhotoWithPhotos>>

    @Delete
    fun deleteHistoryPhoto(historyPhoto: HistoryPhotoDbModel)

    @Delete
    fun deleteHistoryPhotoRef(historyPhotoRef: HistoryPhotoRef)

    @Query("DELETE FROM history_photo_with_photo WHERE historyPhotoId =:historyPhotoId")
    fun deleteHistoryPhotoRefByIds(historyPhotoId: Long)

}