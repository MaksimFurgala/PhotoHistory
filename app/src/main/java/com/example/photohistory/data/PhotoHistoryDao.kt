package com.example.photohistory.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.photohistory.data.db.models.PhotoDbModel

@Dao
interface PhotoHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPhoto(photoDbModel: PhotoDbModel)

    @Delete
    fun deletePhoto(photoDbModel: PhotoDbModel)

    @Query("SELECT * FROM photos WHERE id=:photoId LIMIT 1")
    fun getPhoto(photoId: Int)

    @Update
    fun editPhoto(photoDbModel: PhotoDbModel)

    @Query("SELECT * FROM photos")
    fun getPhotoList(): LiveData<List<PhotoDbModel>>

    @Query("SELECT * FROM ")
    fun getPhotoListOfHistoryPhoto()
}