package com.example.photohistory.domain.models

import com.example.photohistory.domain.models.HistoryPhoto.Companion.UNDEFINED_ID

/**
 * Линия жизни фото-истории.
 *
 * @property name - наименование
 * @property lifeLineText - текст линии жизни
 * @property id - id
 * @constructor Create empty Life line
 */
data class LifeLine(
    val name: String,
    val lifeLineText: String = DEFAULT_LIFE_LINE_TEXT,
    val id: Long = UNDEFINED_ID
) {
    companion object {
        private const val DEFAULT_LIFE_LINE_TEXT = "..."
    }
}
