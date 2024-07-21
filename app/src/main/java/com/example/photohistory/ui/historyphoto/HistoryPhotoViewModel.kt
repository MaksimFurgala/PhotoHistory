package com.example.photohistory.ui.historyphoto

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photohistory.domain.models.HistoryPhoto
import com.example.photohistory.domain.models.Photo
import com.example.photohistory.domain.usecases.HistoryPhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryPhotoViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val historyPhotoUseCase: HistoryPhotoUseCase
) : ViewModel() {

    private val _selectedHistoryPhoto = MutableLiveData<MutableSet<HistoryPhoto>>()
    val selectedPhotos: LiveData<MutableSet<HistoryPhoto>>
        get() = _selectedHistoryPhoto

    val historyPhotoList = historyPhotoUseCase.getHistoryPhotoList()

    /**
     * Удаление фото-истории.
     *
     * @param historyPhoto - фото-история
     */
    fun deleteHistoryPhoto(historyPhoto: HistoryPhoto) {
        viewModelScope.launch {
            historyPhotoUseCase.deleteHistoryPhoto(historyPhoto)
        }
    }
}