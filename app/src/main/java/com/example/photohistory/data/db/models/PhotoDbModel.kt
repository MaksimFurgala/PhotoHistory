package com.example.photohistory.data.db.models

import android.location.Location
import android.os.Parcelable
import androidx.annotation.Size
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.photohistory.domain.models.HistoryPhoto.Companion.UNDEFINED_ID
import kotlinx.parcelize.Parcelize

/**
 * Фото.
 *
 * @property name - наименование
 * @property path - путь к файлу
 * @property description - описание
 * @property location - геолокация
 * @property id - id
 * @constructor Create empty Photo
 */
@Entity(tableName = "photos")
data class PhotoDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val path: String,
    val description: String,
    val location: Location? = null,
)