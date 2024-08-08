package com.example.photohistory.ui.lifeline

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.photohistory.domain.models.HistoryPhoto
import com.example.photohistory.domain.models.Photo
import com.example.photohistory.domain.usecases.PhotoUseCase
import com.yandex.mapkit.map.PlacemarkMapObject
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class LifeLineViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val photoUseCase: PhotoUseCase
) : ViewModel() {

    val photoList by lazy {
        if (_selectedHistoryPhoto.value != null) {
            photoUseCase.getPhotoListOfHistoryPhoto(_selectedHistoryPhoto.value!!)
        } else {
            photoUseCase.getPhotoList()
        }
    }
    //val photoList = photoUseCase.getPhotoList()
//    val photoListOfHistoryPhoto by lazy {
//        photoUseCase.getPhotoListOfHistoryPhoto(selectedHistoryPhoto.value!!)
//    }

    private val _selectedHistoryPhoto = MutableLiveData<HistoryPhoto>()
    val selectedHistoryPhoto: LiveData<HistoryPhoto>
        get() = _selectedHistoryPhoto

    private val _placemarkPhotos = MutableLiveData<MutableMap<Photo, PlacemarkMapObject>>()
    val placemarkPhotos: LiveData<MutableMap<Photo, PlacemarkMapObject>>
        get() = _placemarkPhotos

    init {
        _placemarkPhotos.value = mutableMapOf()
    }

    fun addPlacemark(photo: Photo, placemarkMapObject: PlacemarkMapObject) {
        _placemarkPhotos.value?.plusAssign(photo to placemarkMapObject)
    }

    fun setSelectedHistoryPhoto(historyPhoto: HistoryPhoto) {
        _selectedHistoryPhoto.value = historyPhoto
    }
}