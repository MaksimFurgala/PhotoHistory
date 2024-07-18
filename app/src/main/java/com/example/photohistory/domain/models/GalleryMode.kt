package com.example.photohistory.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Режим интерактива для фрагмента Галлерея.
 *
 * @constructor Create empty Gallery mode
 */
@Parcelize
enum class GalleryMode : Parcelable {
    SHOW,
    SELECT
}