package com.example.photohistory.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photohistory.domain.models.UserSettings
import com.example.photohistory.domain.usecases.SettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text


    private val _isFirstLaunchApp = MutableLiveData<Boolean>()
    val isFirstLaunchApp: LiveData<Boolean>
        get() = _isFirstLaunchApp

    private val _needUpdateUserSettings = MutableLiveData<Boolean>()
    val needUpdateUserSettings: LiveData<Boolean>
        get() = _needUpdateUserSettings

    @Inject
    lateinit var settingsUseCase: SettingsUseCase


    private val _userSettings = MutableLiveData<UserSettings>()
    val userSettings: LiveData<UserSettings>
        get() = _userSettings

    /**
     * Загрузка данных по тек. пользователю.
     *
     */
    fun launchUserData() {
        viewModelScope.launch {
            _userSettings.value = settingsUseCase.getUserSettings()
        }
    }

    /**
     * Обновление настроек пользователя.
     *
     */
    fun updateUserSettings(userSettings: UserSettings) {
        viewModelScope.launch {
            settingsUseCase.updateUserSettings(userSettings)
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}