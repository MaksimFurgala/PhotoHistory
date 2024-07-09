package com.example.photohistory.ui.historyphotoitem

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
class HistoryPhotoItemViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val historyPhotoUseCase: HistoryPhotoUseCase
) : ViewModel() {

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputPhotos = MutableLiveData<Boolean>()
    val errorInputPhotos: LiveData<Boolean>
        get() = _errorInputPhotos

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    /**
     * Добавление новой фото-истории.
     *
     * @param inputName - наименование
     * @param photos - список фото
     */
    fun addNewHistoryPhoto(inputName: String, photos: List<Photo>) {
        val name = parseName(inputName)
        val fieldsValidate = validateInput(name, photos)
        if (fieldsValidate) {
            viewModelScope.launch {
                val historyPhoto = HistoryPhoto(name, photos)
                historyPhotoUseCase.addHistoryPhoto(historyPhoto)
                finishWork()
            }
        }
    }

    /**
     * Парсинг наименования фото истории из контрола.
     *
     * @param inputName - контрол наименования.
     * @return - наименование
     */
    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun validateInput(name: String, photos: List<Photo>): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (!photos.any()) {
            _errorInputPhotos.value = true
            result = false
        }
        return result
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }
}