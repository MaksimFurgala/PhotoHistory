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

    //region LiveData свойства
    /**
     * Ошибка при вводе наименования.
     */
    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    /**
     * Ошибка при выборе фото.
     */
    private val _errorInputPhotos = MutableLiveData<Boolean>()
    val errorInputPhotos: LiveData<Boolean>
        get() = _errorInputPhotos

    /**
     * Событие для закрытия экрана по добавлению фото-истории.
     */
    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    /**
     * Список выбранных фото.
     */
    private val _selectedPhotos = MutableLiveData<MutableList<Photo>>()
    val selectedPhotos: LiveData<MutableList<Photo>>
        get() = _selectedPhotos

    /**
     * Тек. фото-история.
     */
    private val _currentHistoryPhoto = MutableLiveData<HistoryPhoto>()
    val currentHistoryPhoto: LiveData<HistoryPhoto>
        get() = _currentHistoryPhoto

    //endregion

    /**
     * Добавление новой фото-истории.
     *
     * @param inputName - наименование
     * @param photos - список фото
     */
    fun addHistoryPhoto(inputName: String, photos: List<Photo>) {
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
     * Редактирование фото-истории.
     *
     * @param inputName - наименование
     * @param photos - список фото
     */
    fun editHistoryPhoto(inputName: String?, photos: List<Photo>) {
        val nameHistoryPhoto = parseName(inputName)
        val fieldsValidate = validateInput(nameHistoryPhoto, photos)
        if (fieldsValidate) {
            _currentHistoryPhoto.value?.let {
                viewModelScope.launch {
                    val historyPhoto = it.copy(name = nameHistoryPhoto, photos = photos)
                    historyPhotoUseCase.editHistoryPhoto(historyPhoto)
                    finishWork()
                }
            }
        }
    }

    /**
     * Обновление тек. фото истории и списка выбранных фото.
     *
     * @param historyPhoto
     */
    fun updateCurrentHistoryPhoto(historyPhoto: HistoryPhoto) {
        _currentHistoryPhoto.value = historyPhoto
        _selectedPhotos.value = historyPhoto.photos.toMutableList()
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

    /**
     * Валидация пользовательского ввода.
     *
     * @param name - наименование
     * @param photos - коллекция фото
     * @return - результат валидности введенных/выбранных данных
     */
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

    /**
     * Сброс ошибок для контрола Наименование
     *
     */
    public fun resetErrorInputName() {
        _errorInputName.value = false
    }

    /**
     * Сброс ошибки для выбора фото.
     *
     */
    public fun resetErrorInputCount() {
        _errorInputPhotos.value = false
    }

    /**
     * Завершение работы.
     *
     */
    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }
}