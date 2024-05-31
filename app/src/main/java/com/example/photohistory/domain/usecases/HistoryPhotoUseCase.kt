package com.example.photohistory.domain.usecases

import androidx.lifecycle.LiveData
import com.example.photohistory.di.DaggerAppComponent
import com.example.photohistory.domain.models.HistoryPhoto
import com.example.photohistory.domain.models.Photo
import com.example.photohistory.domain.repository.PhotoHistoryRepository
import javax.inject.Inject


class HistoryPhotoUseCase @Inject constructor(private val repository: PhotoHistoryRepository) {
    init {
        DaggerAppComponent.create().inject(this)
    }

    suspend fun addHistoryPhoto(historyPhoto: HistoryPhoto) {
        repository.addHistoryPhoto(historyPhoto)
    }

    suspend fun deleteHistoryPhoto(historyPhoto: HistoryPhoto) {
        repository.deleteHistoryPhoto(historyPhoto)
    }

    suspend fun getHistoryPhoto(historyPhotoId: Int): HistoryPhoto {
        return repository.getHistoryPhoto(historyPhotoId)
    }

    suspend fun editHistoryPhoto(historyPhoto: HistoryPhoto) {
        repository.editHistoryPhoto(historyPhoto)
    }

    fun getHistoryPhotoList(): LiveData<List<HistoryPhoto>> {
        return repository.getHistoryPhotoList()
    }

}