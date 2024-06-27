package com.example.photohistory.domain.usecases

import com.example.photohistory.domain.models.UserSettings
import com.example.photohistory.domain.repository.UserRepository
import javax.inject.Inject

class SettingsUseCase @Inject constructor(val repository: UserRepository){

    fun getUserSettings(): UserSettings {
        return repository.getUserSettings()
    }

    /**
     * Обновление настроек пользователя, с учетом дополнительных условий.
     *
     * @param userSettings - настройки пользователя
     */
    suspend fun updateUserSettings(userSettings: UserSettings) {
        if (!userSettings.firstLaunchAppIsElapsed)
            userSettings.firstLaunchAppIsElapsed = true
        return repository.putUserSettings(userSettings)
    }
}