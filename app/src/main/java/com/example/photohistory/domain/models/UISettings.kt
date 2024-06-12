package com.example.photohistory.domain.models

/**
 * Настройки приложения.
 *
 * @constructor Create empty Settings
 */
data class UISettings(
    val currentThemeStyle: ThemeStyle,
    val currentTab: Int
)