package com.example.photohistory.ui.historyphoto

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photohistory.domain.models.HistoryPhoto
import com.example.photohistory.domain.usecases.HistoryPhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HistoryPhotoViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val historyPhotoUseCase: HistoryPhotoUseCase
) : ViewModel() {

    private val _selectedHistoryPhoto = MutableLiveData<MutableSet<HistoryPhoto>>()
    val selectedPhotos: LiveData<MutableSet<HistoryPhoto>>
        get() = _selectedHistoryPhoto

    /**
     * LiveData для признака, что фото-история выбрана.
     */
    private val _checkedHistoryPhotoIsEnabled = MutableLiveData<Boolean>()
    val checkedHistoryPhotoIsEnabled: LiveData<Boolean>
        get() = _checkedHistoryPhotoIsEnabled

    private val _currentHistoryPhoto = MutableLiveData<Pair<Int, HistoryPhoto>?>()
    val currentHistoryPhoto: LiveData<Pair<Int, HistoryPhoto>?>
        get() = _currentHistoryPhoto

    private val _oldHistoryPhoto = MutableLiveData<Pair<Int, HistoryPhoto>>()
    val oldHistoryPhoto: LiveData<Pair<Int, HistoryPhoto>>
        get() = _oldHistoryPhoto

    val historyPhotoList = historyPhotoUseCase.getHistoryPhotoList()

    /**
     * Удаление фото-истории.
     *
     * @param historyPhoto - фото-история
     */
    fun deleteHistoryPhoto() {
        if (currentHistoryPhoto.value == null) {
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            historyPhotoUseCase.deleteHistoryPhoto(currentHistoryPhoto.value?.second!!)
            withContext(Dispatchers.Main) {
                _currentHistoryPhoto.value = null
            }
        }
    }

    fun updateCurrentHistoryPhoto(status: Boolean) {
        _currentHistoryPhoto.value?.second?.isChecked = status
    }

    fun setCurrentHistoryPhoto(historyPhoto: HistoryPhoto, position: Int) {
        _currentHistoryPhoto.value = position to historyPhoto
    }
}