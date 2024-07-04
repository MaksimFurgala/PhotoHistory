package com.example.photohistory.ui.gallery

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photohistory.R
import com.example.photohistory.databinding.FragmentGalleryBinding
import com.example.photohistory.domain.models.Photo
import com.example.photohistory.ui.PhotoListAdapter
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GalleryFragment : Fragment() {
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    val galleryViewModel by viewModels<GalleryViewModel>()

    @Inject
    lateinit var photoListAdapter: PhotoListAdapter

    private lateinit var contractImageCapture: ActivityResultLauncher<Uri>

    @SuppressLint("Recycle")
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

                Picasso.get()
                    .load(galleryViewModel.newImageUri.value)
                    .placeholder(R.drawable.fab_camera_white)
                    .error(R.drawable.photo_error_white)
                    .into(binding.ivNewPhoto)
            } else {
                // В случае отмены логируем и удаляем временный файл.
                Log.d("GalleryFragment", "ImageCapture is cancelled.")
                galleryViewModel.newImageUri.value?.let { uri -> context.contentResolver.delete(
                    uri,
                    null,
                    null
                ) }
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
            val orientation = context.resources.configuration.orientation
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                layoutManager = GridLayoutManager(context, 3)
            } else {
                layoutManager = GridLayoutManager(context, 5)
            }
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
            //val multipleChecked = galleryViewModel.multipleCheckedPhotosIsEnabled.value
            onPhotoClickListener = { photo, position ->
                if (galleryViewModel.multipleCheckedPhotosIsEnabled.value == true) {
                    changePhotoState(photo)
                    notifyItemChanged(position)
                }
            }

            onPhotoLongClickListener = { photo, position ->
                if (galleryViewModel.multipleCheckedPhotosIsEnabled.value != true) {
                    galleryViewModel.updateMultipleCheckedPhotos(true)
                }
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
            galleryViewModel.removeSelectedPhoto(photo)
        } else {
            photo.isChecked = true
            galleryViewModel.addSelectedPhoto(photo)
        }
        if (galleryViewModel.selectedPhotos.value?.any() != true) {
            galleryViewModel.updateMultipleCheckedPhotos(false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}