package com.example.photohistory.domain.models

import android.location.Location
import androidx.lifecycle.LiveData

/**
 * Фото-история (содержит коллекцию фотографий).
 *
 * @property name - наименование
 * @property photos - коллекция фото
 * @property id - id
 * @constructor Create empty History photo
 */
data class HistoryPhoto(
    val name: String,
    val photos: List<Photo>,
    val id: Int = UNDEFINED_ID
) {

    companion object {
        const val UNDEFINED_ID = 0
    }
}
