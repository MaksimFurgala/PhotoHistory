package com.example.photohistory.app

import android.app.Application
import com.example.photohistory.BuildConfig
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        // Устанавливаем api ключ для MapView.
        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)
    }
}