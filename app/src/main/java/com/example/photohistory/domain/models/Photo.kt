package com.example.photohistory.domain.models

import android.os.Parcelable
import com.example.photohistory.domain.models.HistoryPhoto.Companion.UNDEFINED_ID
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

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
@Parcelize
data class Photo(
    val name: String,
    val path: String?,
    val dataBase64: String? = null,
    val description: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val photoId: Long = UNDEFINED_ID,
): Parcelable {

    @IgnoredOnParcel
    var isChecked: Boolean = false

    companion object {
        private const val DEFAULT_DESCRIPTION = ""
    }
}
