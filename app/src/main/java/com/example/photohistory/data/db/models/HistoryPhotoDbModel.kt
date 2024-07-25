package com.example.photohistory.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_photos")
data class HistoryPhotoDbModel(
    @PrimaryKey(autoGenerate = true)
    val historyPhotoId: Long,
    val name: String,
)
