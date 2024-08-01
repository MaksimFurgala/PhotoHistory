package com.example.photohistory.ui.gallery

import android.Manifest
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
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photohistory.R
import com.example.photohistory.databinding.FragmentGalleryBinding
import com.example.photohistory.domain.models.GalleryMode
import com.example.photohistory.domain.models.HistoryPhoto
import com.example.photohistory.domain.models.LocationModel
import com.example.photohistory.domain.models.Photo
import com.example.photohistory.ui.PhotoListAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Gallery fragment
 *
 * @constructor Create empty Gallery fragment
 */
@AndroidEntryPoint
class GalleryFragment : Fragment() {
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<GalleryFragmentArgs>()
    val galleryViewModel by viewModels<GalleryViewModel>()
    var currentHistoryPhoto: HistoryPhoto? = null

    @Inject
    lateinit var fusedLocationClient: FusedLocationProviderClient

    private var galleryMode = GalleryMode.SHOW

    @Inject
    lateinit var photoListAdapter: PhotoListAdapter

    private lateinit var contractImageCapture: ActivityResultLauncher<Uri>
    private lateinit var locationPermissionRequest: ActivityResultLauncher<Array<String>>

    @SuppressLint("Recycle", "MissingPermission")
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

                // Устанвыливаем превью фото через Picasso в спец. imageView.
                Picasso.get()
                    .load(galleryViewModel.newImageUri.value)
                    .placeholder(R.drawable.fab_camera_white)
                    .error(R.drawable.photo_error_white)
                    .into(binding.ivNewPhoto)
            } else {
                // В случае отмены логируем и удаляем временный файл.
                Log.d("GalleryFragment", "ImageCapture is cancelled.")
                galleryViewModel.newImageUri.value?.let { uri ->
                    context.contentResolver.delete(
                        uri,
                        null,
                        null
                    )
                }
            }
        }

        // Регистрируем новый контракт Разрешения на предоставления точного и приблизительного местоположения.
        locationPermissionRequest =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                when {
                    permissions.getOrDefault(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        false
                    ) || permissions.getOrDefault(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        false
                    ) -> {
                        fusedLocationClient.getCurrentLocation(
                            Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                            CancellationTokenSource().token
                        ).apply {
                            addOnCompleteListener {
                                galleryViewModel.updateCurrentLocationModel(
                                    LocationModel(
                                        result.latitude, result.longitude, true
                                    )
                                )
                            }
                        }
                    }

                    else -> {
                        galleryViewModel.updateCurrentLocationModel(LocationModel(0.0, 0.0, true))
                    }
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        parseParams()
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
                locationPermissionRequest.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )

                galleryViewModel.createImageUri()
                contractImageCapture.launch(galleryViewModel.newImageUri.value)
            }

            when (galleryMode) {
                GalleryMode.SELECT -> {
                    btnSelectedPhotos.setOnClickListener {
                        findNavController().navigate(
                            GalleryFragmentDirections.actionNavGalleryToHistoryPhotoItemFragment(
                                currentHistoryPhoto?.copy(
                                    photos = galleryViewModel.selectedPhotos.value?.toList()
                                        ?: emptyList()
                                )
                            )
                        )
                    }

                    //region Скрываем UI элементы
                    fabCreatePhoto.isGone = true
                    fabDeletePhoto.isGone = true
                    newPhotoPanel.isGone = true
                    //endregion
                }

                GalleryMode.SHOW -> {

                }
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
                if (galleryViewModel.multipleCheckedPhotosIsEnabled.value == true) {
                    changePhotoState(photo)
                    notifyItemChanged(position)
                } else {
                    findNavController().navigate(
                        GalleryFragmentDirections.actionNavGalleryToNavPhoto(
                            photo
                        )
                    )
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
     * Парсинг аргументов из текущего фрагмента, которые переданы из activity или фрагмента.
     *
     */
    private fun parseParams() {
        try {
            galleryMode = args.galleryMode!!
            currentHistoryPhoto = args.historyPhoto
        } catch (ex: Exception) {
            Log.d("GalleryFragmentParseParams", "${ex.message} ${ex.stackTrace.toString()}")
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

        // Выбран режим выбора фотографий
        if (galleryMode == GalleryMode.SELECT) {
            binding.btnSelectedPhotos.text = getString(
                R.string.selected_photos_text_btn,
                galleryViewModel.selectedPhotos.value?.size ?: 0
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}