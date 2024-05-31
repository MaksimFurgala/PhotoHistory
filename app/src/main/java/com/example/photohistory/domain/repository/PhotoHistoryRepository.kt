package com.example.photohistory.domain.repository

import androidx.lifecycle.LiveData
import com.example.photohistory.domain.models.HistoryPhoto
import com.example.photohistory.domain.models.LifeLine
import com.example.photohistory.domain.models.Photo
import com.example.photohistory.domain.models.UISettings

interface PhotoHistoryRepository {

    //region Фото
    suspend fun addPhoto(photo: Photo)
    suspend fun deletePhoto(photo: Photo)
    suspend fun getPhoto(photoId: Int): Photo
    suspend fun editPhoto(photo: Photo)
    fun getPhotoListOfHistoryPhoto(historyPhoto: HistoryPhoto): LiveData<List<Photo>>
    fun getPhotoList(): LiveData<List<Photo>>
    //endregion

    //region Фото-история
    suspend fun addHistoryPhoto(historyPhoto: HistoryPhoto)
    suspend fun deleteHistoryPhoto(historyPhoto: HistoryPhoto)
    suspend fun getHistoryPhoto(historyPhotoId: Int): HistoryPhoto
    suspend fun editHistoryPhoto(historyPhoto: HistoryPhoto)
    fun getHistoryPhotoList(): LiveData<List<HistoryPhoto>>
    //endregion

    //region Настройки
    fun getUISettings(): LiveData<UISettings>
    //endregion

    //region Lifeline для фото-истории
    fun getLifeLineOfHistoryPhoto(): LiveData<LifeLine>
    //endregion

}