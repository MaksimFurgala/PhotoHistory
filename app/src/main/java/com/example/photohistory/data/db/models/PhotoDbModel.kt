package com.example.photohistory.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Фото.
 *
 * @property name - наименование
 * @property path - путь к файлу
 * @property description - описание
 * @property location - геолокация
 * @property photoId - id
 * @constructor Create empty Photo
 */
@Entity(tableName = "photos")
data class PhotoDbModel(
    @PrimaryKey(autoGenerate = true)
    val photoId: Int,
    val name: String,
    val path: String?,
    val dataBase64: String?,
    val description: String?,
    val latitude: Double?,
    val longitude: Double?
)