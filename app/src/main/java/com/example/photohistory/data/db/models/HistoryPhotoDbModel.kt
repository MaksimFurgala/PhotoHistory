package com.example.photohistory.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_photos")
data class HistoryPhotoDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
//    val photos: List<PhotoDbModel>,
)
