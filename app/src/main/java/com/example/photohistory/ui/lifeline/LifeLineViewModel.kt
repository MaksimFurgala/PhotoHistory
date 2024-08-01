package com.example.photohistory.ui.lifeline

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.photohistory.domain.usecases.PhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class LifeLineViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val photoUseCase: PhotoUseCase
) : ViewModel() {

    val photoList = photoUseCase.getPhotoList()
}