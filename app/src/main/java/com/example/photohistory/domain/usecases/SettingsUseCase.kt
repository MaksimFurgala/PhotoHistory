package com.example.photohistory.domain.usecases

import androidx.lifecycle.LiveData
import com.example.photohistory.domain.models.UISettings
import com.example.photohistory.domain.repository.PhotoHistoryRepository
import javax.inject.Inject

class SettingsUseCase @Inject constructor(private val repository: PhotoHistoryRepository){

    /**
     * Получение UI настроек.
     *
     * @return UI настройки
     */
    suspend fun getUISettings(): LiveData<UISettings> {
        return repository.getUISettings()
    }
}