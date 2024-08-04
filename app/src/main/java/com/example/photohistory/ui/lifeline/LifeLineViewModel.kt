package com.example.photohistory.ui.lifeline

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    val photoList = photoUseCase.getPhotoList()

    private val _placemarkPhotos = MutableLiveData<MutableMap<Photo, PlacemarkMapObject>>()
    val placemarkPhotos: LiveData<MutableMap<Photo, PlacemarkMapObject>>
        get() = _placemarkPhotos

    init {
        _placemarkPhotos.value = mutableMapOf()
    }

    fun addPlacemark(photo: Photo, placemarkMapObject: PlacemarkMapObject) {
        _placemarkPhotos.value?.plusAssign(photo to placemarkMapObject)
    }
}