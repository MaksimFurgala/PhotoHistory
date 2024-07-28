package com.example.photohistory.domain.models

/**
 * Модель для геолокации.
 *
 * @property latitude
 * @property longitude
 * @property isGranted
 * @constructor Create empty Location model
 */
data class LocationModel(
    val latitude: Double,
    val longitude: Double,
    val isGranted: Boolean
)
