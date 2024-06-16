package com.example.photohistory.data

import androidx.lifecycle.LiveData
import com.example.photohistory.domain.models.HistoryPhoto
import com.example.photohistory.domain.models.LifeLine
import com.example.photohistory.domain.models.Photo
import com.example.photohistory.domain.models.UISettings
import com.example.photohistory.domain.repository.PhotoHistoryRepository
import javax.inject.Inject

class PhotoHistoryRepositoryImpl (): PhotoHistoryRepository {
    override suspend fun addPhoto(photo: Photo) {
        TODO("Not yet implemented")
    }

    override suspend fun deletePhoto(photo: Photo) {
        TODO("Not yet implemented")
    }

    override suspend fun getPhoto(photoId: Int): Photo {
        TODO("Not yet implemented")
    }

    override suspend fun editPhoto(photo: Photo) {
        TODO("Not yet implemented")
    }

    override fun getPhotoList(): LiveData<List<Photo>> {
        TODO("Not yet implemented")
    }

    override fun getPhotoListOfHistoryPhoto(historyPhoto: HistoryPhoto): LiveData<List<Photo>> {
        TODO("Not yet implemented")
    }

    override suspend fun addHistoryPhoto(historyPhoto: HistoryPhoto) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteHistoryPhoto(historyPhoto: HistoryPhoto) {
        TODO("Not yet implemented")
    }

    override suspend fun getHistoryPhoto(historyPhotoId: Int): HistoryPhoto {
        TODO("Not yet implemented")
    }

    override suspend fun editHistoryPhoto(historyPhoto: HistoryPhoto) {
        TODO("Not yet implemented")
    }

    override fun getHistoryPhotoList(): LiveData<List<HistoryPhoto>> {
        TODO("Not yet implemented")
    }

    override fun getUISettings(): LiveData<UISettings> {
        TODO("Not yet implemented")
    }

    override fun getLifeLineOfHistoryPhoto(): LiveData<LifeLine> {
        TODO("Not yet implemented")
    }
}