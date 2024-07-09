package com.example.photohistory.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.photohistory.data.db.models.HistoryPhotoDbModel
import com.example.photohistory.data.db.models.HistoryPhotoWithPhotos
import com.example.photohistory.data.db.models.PhotoDbModel
import com.example.photohistory.domain.models.HistoryPhoto
import com.example.photohistory.domain.models.LifeLine
import com.example.photohistory.domain.models.Photo
import com.example.photohistory.domain.models.UISettings
import com.example.photohistory.domain.repository.PhotoHistoryRepository
import kotlinx.coroutines.flow.emptyFlow
import java.lang.RuntimeException
import javax.inject.Inject

class PhotoHistoryRepositoryImpl @Inject constructor(
    val photoHistoryDao: PhotoHistoryDao,
    val mapper: DatabaseMapper
) : PhotoHistoryRepository {

    override suspend fun addPhoto(photo: Photo) {
        photoHistoryDao.addPhoto(
            mapper.photoToPhotoDbModel(photo)
        )
    }

    override suspend fun deletePhoto(photo: Photo) {
        photoHistoryDao.deletePhoto(
            mapper.photoToPhotoDbModel(photo)
        )
    }

    override suspend fun getPhoto(photoId: Int): Photo {
        return mapper.photoDbModelToPhoto(
            photoHistoryDao.getPhoto(photoId)
        )
    }

    override suspend fun editPhoto(photo: Photo) {
        photoHistoryDao.editPhoto(
            mapper.photoToPhotoDbModel(photo)
        )
    }

    override fun getPhotoList(): LiveData<List<Photo>> {
        return MediatorLiveData<List<Photo>>().apply {
            addSource(photoHistoryDao.getPhotoList()) { listDbModel ->
                value = listDbModel.map { mapper.photoDbModelToPhoto(it) }
            }
        }
    }

    override fun getPhotoListOfHistoryPhoto(historyPhoto: HistoryPhoto): LiveData<List<Photo>> {
        TODO("Not yet implemented")
    }

    override suspend fun addHistoryPhoto(historyPhoto: HistoryPhoto) {
        val historyPhotoDbModel = mapper.historyPhotoToHistoryPhotoDbModel(historyPhoto)
        photoHistoryDao.addHistoryPhoto(historyPhotoDbModel)
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