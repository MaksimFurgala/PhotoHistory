package com.example.photohistory.ui.gallery

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.photohistory.domain.usecases.PhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(@ApplicationContext val context: Context) : ViewModel() {

    @Inject
    lateinit var photoUseCase: PhotoUseCase

    private val _newImageUri = MutableLiveData<Uri>()
    val newImageUri: LiveData<Uri>
        get() = _newImageUri

    private val _newImageFile = MutableLiveData<File?>()
    val newImageFile: LiveData<File?>
        get() = _newImageFile

    /**
     * Создание Uri для файла новой фотографии и обновление свойств для Uri и File.
     *
     * @return Uri файла
     */
    fun createImageUri(): Uri {
        // Создаем временный файл для сохранения фото
        val photoFile: File? = try {
            createImageFile()
        } catch (ex: IOException) {
            Log.e("GalleryViewModel", "Error creating tempFile.")
            null
        }

        // Получаем Uri для файла и обновляем значения для объектов liveData.
        return FileProvider.getUriForFile(
            context,
            "com.example.photohistory.FileProvider",
            photoFile!!
        ).also {
            _newImageUri.value = it
            _newImageFile.value = photoFile
        }
    }

    /**
     * Создание файла для нового фото.
     *
     * @return файл
     */
    @SuppressLint("SimpleDateFormat")
    private fun createImageFile(): File {
        // Создаем уникальное имя файла на основе даты и времени
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
    }

}