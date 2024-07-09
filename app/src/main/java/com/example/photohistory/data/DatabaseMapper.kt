package com.example.photohistory.data

import com.example.photohistory.data.db.models.HistoryPhotoDbModel
import com.example.photohistory.data.db.models.PhotoDbModel
import com.example.photohistory.domain.models.HistoryPhoto
import com.example.photohistory.domain.models.Photo
import org.mapstruct.Mapper

@Mapper
interface DatabaseMapper {
    fun photoToPhotoDbModel(photo: Photo): PhotoDbModel
    fun photoDbModelToPhoto(photoDbModel: PhotoDbModel): Photo
    fun historyPhotoToHistoryPhotoDbModel(historyPhoto: HistoryPhoto): HistoryPhotoDbModel
    fun historyPhotoDbModelToHistoryPhoto(historyPhoto: HistoryPhotoDbModel): HistoryPhoto
}