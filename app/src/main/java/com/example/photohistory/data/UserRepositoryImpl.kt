package com.example.photohistory.data

import android.content.Context
import android.content.SharedPreferences
import com.example.photohistory.domain.models.UserSettings
import com.example.photohistory.domain.repository.UserRepository

class UserRepositoryImpl (val context: Context) : UserRepository {
    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(
            USER_SETTINGS_TABLE_NAME,
            Context.MODE_PRIVATE
        )
    }

    /**
     * Угиверсальный метод для записи данных (настроек пользователя) в SharedPreferences.
     *
     * @param key - ключ
     * @param value - значение
     */
    override suspend fun putUserSettings(settings: UserSettings) {
        sharedPreferences.edit().apply {
            putBoolean(FIRST_LAUNCH_APP_ELAPSED_KEY, settings.firstLaunchAppIsElapsed)
        }.apply()
    }

    /**
     * Получение данных (настроек пользователя)
     *
     * @param key
     * @param value
     * @return
     */
    override fun getUserSettings(): UserSettings {
        return UserSettings(
            firstLaunchAppIsElapsed = sharedPreferences.getBoolean(
                FIRST_LAUNCH_APP_ELAPSED_KEY,
                false
            )
        )
    }

    companion object {
        private val USER_SETTINGS_TABLE_NAME = "photo_history_user_settings"
        private val FIRST_LAUNCH_APP_ELAPSED_KEY = "is_first_launch_app"
    }
}