package com.example.photohistory.domain.usecases

import androidx.lifecycle.LiveData
import com.example.photohistory.domain.models.UISettings
import com.example.photohistory.domain.models.UserSettings
import com.example.photohistory.domain.repository.PhotoHistoryRepository
import com.example.photohistory.domain.repository.UserRepository
import javax.inject.Inject

class SettingsUseCase (val repository: UserRepository){

//    /**
//     * Получение UI настроек.
//     *
//     * @return UI настройки
//     */
//    suspend fun getUISettings(): LiveData<UISettings> {
//        return repository.getUISettings()
//    }

    fun getUserSettings(): UserSettings {
        return repository.getUserSettings()
    }

    suspend fun updateUserSettings(userSettings: UserSettings) {
        return repository.putUserSettings(userSettings)
    }
}