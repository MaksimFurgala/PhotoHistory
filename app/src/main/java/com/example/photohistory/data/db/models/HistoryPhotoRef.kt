package com.example.photohistory.data.db.models

import androidx.room.Entity

@Entity(
    tableName = "history_photo_with_photo",
    primaryKeys = ["historyPhotoId", "photoId"]
)
data class HistoryPhotoRef(
    val photoId: Long,
    val historyPhotoId: Long
)
