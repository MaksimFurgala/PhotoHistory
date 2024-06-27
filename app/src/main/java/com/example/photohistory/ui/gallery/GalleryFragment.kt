package com.example.photohistory.ui.gallery

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.photohistory.databinding.FragmentGalleryBinding
import com.example.photohistory.domain.models.Photo
import com.example.photohistory.ui.PhotoListAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GalleryFragment : Fragment() {
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    // Включен ли режим для выбора нескольких фото из списка
    private var multipleCheckedItemsIsEnabled = false

    // Количество выбранных фотографий
    private var countOfSelectedPhoto: Int = 0

    val galleryViewModel by viewModels<GalleryViewModel>()

    @Inject
    lateinit var photoListAdapter: PhotoListAdapter

    private lateinit var contractImageCapture: ActivityResultLauncher<Uri>

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Регистрируем новый контракт Камера и инициализируем обработчик результата.
        contractImageCapture = registerForActivityResult(ActivityResultContracts.TakePicture()) {
            if (it) {
                val photo = Photo(
                    galleryViewModel.newImageUri.value?.lastPathSegment!!,
                    galleryViewModel.newImageUri.value.toString()
                )
                galleryViewModel.addPhoto(photo)
                binding.ivNewPhoto.setImageURI(galleryViewModel.newImageUri.value)

            } else {
                // В случае отмены логируем и удаляем временный файл.
                Log.d("GalleryFragment", "ImageCapture is cancelled.")
                galleryViewModel.newImageFile.value?.delete()
            }
        }
    }

    /**
     * Настройка recyclerView.
     *
     */
    private fun setupRecyclerView() {
        with(binding.rvPhotoList) {
            adapter = photoListAdapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = galleryViewModel

            // Создаем обработчик события создания нового фото: создание нового Uri и запуск контракта.
            fabCreatePhoto.setOnClickListener {
                galleryViewModel.createImageUri()
                contractImageCapture.launch(galleryViewModel.newImageUri.value)
            }
        }

        setupRecyclerView()

        with(galleryViewModel) {
            photoList.observe(viewLifecycleOwner) {
                photoListAdapter.submitList(it)
            }
        }

        setupAdapterClickListener()
    }

    /**
     * Установка слушателей кликов для обычного и долгого нажатия для адаптера в recyclerView.
     *
     */
    private fun setupAdapterClickListener() {
        with(photoListAdapter) {
            onPhotoClickListener = { photo, position ->
                if (multipleCheckedItemsIsEnabled) {
                    changePhotoState(photo)
                    notifyItemChanged(position)
                }
            }

            onPhotoLongClickListener = { photo, position ->
                if (!multipleCheckedItemsIsEnabled)
                    multipleCheckedItemsIsEnabled = true
                changePhotoState(photo)
                notifyItemChanged(position)
            }
        }
    }

    /**
     * Изменение состояния для фото в recyclerView при событии клика.
     *
     * @param photo - фото
     */
    private fun changePhotoState(photo: Photo) {
        if (photo.isChecked) {
            photo.isChecked = false
            countOfSelectedPhoto--
        } else {
            photo.isChecked = true
            countOfSelectedPhoto++
        }
        if (countOfSelectedPhoto < 1) {
            multipleCheckedItemsIsEnabled = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}