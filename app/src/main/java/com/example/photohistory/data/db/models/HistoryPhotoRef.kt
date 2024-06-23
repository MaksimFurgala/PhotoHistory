package com.example.photohistory.data.db.models

import androidx.room.Entity

@Entity(tableName = "history_photo_with_photo", primaryKeys = ["photoId", "historyPhotoId"])
data class HistoryPhotoRef(
    val photoId: Int,
    val historyPhotoId: Int
)
