package com.example.photohistory.domain.models

import android.location.Location
import android.os.Parcelable
import androidx.annotation.Size
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
@Parcelize
data class Photo(
    val name: String,
    val path: String,
    val description: String = DEFAULT_DESCRIPTION,
    val location: Location? = null,
    val id: Int = UNDEFINED_ID
): Parcelable {
    companion object {
        private const val DEFAULT_DESCRIPTION = ""
    }
}
