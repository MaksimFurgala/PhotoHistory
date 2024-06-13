package com.example.photohistory.domain.usecases

import com.example.photohistory.domain.models.UserSettings
import com.example.photohistory.domain.repository.UserRepository

class SettingsUseCase (val repository: UserRepository){

    fun getUserSettings(): UserSettings {
        return repository.getUserSettings()
    }

    suspend fun updateUserSettings(userSettings: UserSettings) {
        return repository.putUserSettings(userSettings)
    }
}