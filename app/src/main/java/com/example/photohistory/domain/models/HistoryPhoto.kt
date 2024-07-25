package com.example.photohistory.domain.models

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

/**
 * Фото-история (содержит коллекцию фотографий).
 *
 * @property name - наименование
 * @property photos - коллекция фото
 * @property historyPhotoId - id
 * @constructor Create empty History photo
 */
@Parcelize
data class HistoryPhoto(
    val name: String,
    val photos: List<Photo>,
    val historyPhotoId: Long = UNDEFINED_ID
): Parcelable {
    @IgnoredOnParcel
    var isChecked: Boolean = false

    companion object {
        const val UNDEFINED_ID = 0L
    }
}
