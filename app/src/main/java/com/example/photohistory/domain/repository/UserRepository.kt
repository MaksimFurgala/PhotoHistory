package com.example.photohistory.domain.repository

import com.example.photohistory.domain.models.UserSettings

interface UserRepository {
    suspend fun putUserSettings(settings: UserSettings)
    fun getUserSettings(): UserSettings
}