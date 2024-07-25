package com.example.photohistory.data.db.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class PhotosWithHistoryPhoto(

    @Embedded val photo: PhotoDbModel,
    @Relation(
        parentColumn = "photoId",
        entityColumn = "historyPhotoId",
        associateBy = Junction(HistoryPhotoRef::class)
    )
    val historyPhotos: List<HistoryPhotoDbModel>
)