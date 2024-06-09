package com.example.photohistory.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.photohistory.domain.models.HistoryPhoto
import com.example.photohistory.ui.lifeline.LifeLineViewModel

class HistoryPhotoViewModelFactory(
    private val application: Application,
    private val historyPhoto: HistoryPhoto
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LifeLineViewModel::class.java)) {
            return LifeLineViewModel(application, historyPhoto) as T
        }
        throw RuntimeException("Unknown view model class $modelClass")
    }
}