package com.example.photohistory.ui.lifeline

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.photohistory.domain.models.HistoryPhoto

class LifeLineViewModel(
    private val application: Application,
    private val historyPhoto: HistoryPhoto
) : ViewModel() {

}