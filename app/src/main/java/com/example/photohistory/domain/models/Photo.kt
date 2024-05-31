package com.example.photohistory.domain.models

import android.location.Location
import androidx.annotation.Size
import com.example.photohistory.domain.models.HistoryPhoto.Companion.UNDEFINED_ID

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
data class Photo(
    val name: String,
    val path: String,
    val description: String = DEFAULT_DESCRIPTION,
    val location: Location? = null,
    val id: Int = UNDEFINED_ID
) {
    companion object {
        private const val DEFAULT_DESCRIPTION = ""
    }
}
