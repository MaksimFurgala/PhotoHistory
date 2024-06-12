package com.example.photohistory.domain.usecases

import androidx.lifecycle.LiveData
import com.example.photohistory.domain.models.HistoryPhoto
import com.example.photohistory.domain.models.Photo
import com.example.photohistory.domain.repository.PhotoHistoryRepository

class PhotoUseCase (private val repository: PhotoHistoryRepository) {

    /**
     * Создание нового фото.
     *
     * @param photo - фото
     */
    suspend fun addPhoto(photo: Photo) {
        repository.addPhoto(photo)
    }

    /**
     * Удалить фото.
     *
     * @param photo - фото
     */
    suspend fun deletePhoto(photo: Photo) {
        repository.deletePhoto(photo)
    }

    /**
     * Получить фото.
     *
     * @param photoId - id фото
     * @return фото
     */
    suspend fun getPhoto(photoId: Int): Photo {
        return repository.getPhoto(photoId)
    }

    /**
     * Редактирование фото.
     *
     * @param photo - фото
     */
    suspend fun editPhoto(photo: Photo) {
        repository.editPhoto(photo)
    }

    /**
     * Получить коллекцию фото для фото-истории.
     *
     * @param historyPhoto - фото-история
     * @return - коллекция фото
     */
    fun getPhotoListOfHistoryPhoto(historyPhoto: HistoryPhoto): LiveData<List<Photo>> {
        return repository.getPhotoListOfHistoryPhoto(historyPhoto)
    }

    /**
     * Получить коллекцию фото.
     *
     * @return коллекция фото
     */
    fun getPhotoList(): LiveData<List<Photo>> {
        return repository.getPhotoList()
    }
}