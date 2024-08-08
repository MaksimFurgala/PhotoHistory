package com.example.photohistory.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.photohistory.data.db.models.HistoryPhotoRef
import com.example.photohistory.domain.models.HistoryPhoto
import com.example.photohistory.domain.models.LifeLine
import com.example.photohistory.domain.models.Photo
import com.example.photohistory.domain.models.UISettings
import com.example.photohistory.domain.repository.PhotoHistoryRepository
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
        return MediatorLiveData<List<Photo>>().apply {
            addSource(photoHistoryDao.getPhotoListOfHistoryPhoto(historyPhoto.historyPhotoId)) { listHistoryPhotoWithPhotos ->
                listHistoryPhotoWithPhotos.forEach { historyPhotoWithPhotos ->
                    value = historyPhotoWithPhotos.photos.map {
                        mapper.photoDbModelToPhoto(it)
                    }
                }
            }
        }
    }

    override suspend fun addHistoryPhoto(historyPhoto: HistoryPhoto) {
        val historyPhotoDbModel = mapper.historyPhotoToHistoryPhotoDbModel(historyPhoto)
        val historyPhotoId = photoHistoryDao.addHistoryPhoto(historyPhotoDbModel)
        historyPhoto.photos.forEach { photo ->
            val photoId = photoHistoryDao.addPhoto(mapper.photoToPhotoDbModel(photo))
            photoHistoryDao.addHistoryPhotoWithPhoto(
                HistoryPhotoRef(
                    historyPhotoId = historyPhotoId,
                    photoId = photoId
                )
            )
        }
    }

    /**
     * Удаление фото-истории.
     *
     * @param historyPhoto - фото-история
     */
    override suspend fun deleteHistoryPhoto(historyPhoto: HistoryPhoto) {
        photoHistoryDao.deleteHistoryPhotoRefByIds(historyPhoto.historyPhotoId)
        photoHistoryDao.deleteHistoryPhoto(mapper.historyPhotoToHistoryPhotoDbModel(historyPhoto))
    }

    override suspend fun getHistoryPhoto(historyPhotoId: Int): HistoryPhoto {
        TODO("Not yet implemented")
    }

    override suspend fun editHistoryPhoto(historyPhoto: HistoryPhoto) {
        TODO("Not yet implemented")
    }

    override fun getHistoryPhotoList(): LiveData<List<HistoryPhoto>> {
        return MediatorLiveData<List<HistoryPhoto>>().apply {
            addSource(photoHistoryDao.getHistoryPhotoList()) { listDbModel ->
                value = listDbModel.map {
                    HistoryPhoto(
                        name = it.historyPhotoDbModel.name,
                        photos = it.photos.map { photo -> mapper.photoDbModelToPhoto(photo) },
                        historyPhotoId = it.historyPhotoDbModel.historyPhotoId
                    )
                }
            }
        }
    }

    override fun getUISettings(): LiveData<UISettings> {
        TODO("Not yet implemented")
    }

    override fun getLifeLineOfHistoryPhoto(): LiveData<LifeLine> {
        TODO("Not yet implemented")
    }
}