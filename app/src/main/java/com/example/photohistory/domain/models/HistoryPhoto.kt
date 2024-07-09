package com.example.photohistory.domain.models

import android.location.Location
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.versionedparcelable.NonParcelField
import kotlinx.parcelize.Parcelize

/**
 * Фото-история (содержит коллекцию фотографий).
 *
 * @property name - наименование
 * @property photos - коллекция фото
 * @property id - id
 * @constructor Create empty History photo
 */
@Parcelize
data class HistoryPhoto(
    val name: String,
    @NonParcelField
    val photos: List<Photo>,
    val id: Int = UNDEFINED_ID
): Parcelable {

    companion object {
        const val UNDEFINED_ID = 0
    }
}
