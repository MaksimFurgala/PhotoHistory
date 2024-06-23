package com.example.photohistory.data.db.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class HistoryPhotoWithPhotos(

    @Embedded
    val historyPhotoDbModel: HistoryPhotoDbModel,

    @Relation(
        parentColumn = "historyPhotoId",
        entityColumn = "photoId",
        associateBy = Junction(HistoryPhotoRef::class)
    )
    val photos: List<PhotoDbModel>
)