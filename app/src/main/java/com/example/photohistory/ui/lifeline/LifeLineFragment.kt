package com.example.photohistory.ui.lifeline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photohistory.R
import com.example.photohistory.databinding.FragmentLifeLineBinding
import com.example.photohistory.domain.models.HistoryPhoto
import com.example.photohistory.domain.models.Photo
import com.example.photohistory.ui.PhotoListAdapter
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LifeLineFragment : Fragment() {
    private var _binding: FragmentLifeLineBinding? = null
    private val binding get() = _binding!!

    private val lifeLineViewModel by viewModels<LifeLineViewModel>()
    private val args by navArgs<LifeLineFragmentArgs>()
    var currentHistoryPhoto: HistoryPhoto? = null

    @Inject
    lateinit var photoListAdapter: PhotoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(context)
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        MapKitFactory.getInstance().onStop()
        binding.mapView.onStop()
    }

    /**
     * Настройка recyclerView.
     *
     */
    private fun setupRecyclerView() {
        with(binding.rvPhotoList) {
            adapter = photoListAdapter
            val orientation = context.resources.configuration.orientation
            if (orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT) {
                layoutManager = GridLayoutManager(context, 3)
            } else {
                layoutManager = GridLayoutManager(context, 5)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLifeLineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = lifeLineViewModel
        }

        // Инициализация recyclerView.
        setupRecyclerView()
        parseParams()
        with(lifeLineViewModel) {
            photoList.observe(viewLifecycleOwner) {
                photoListAdapter.submitList(it)
            }
        }

        // Установка слушателей нажатия для элементов recyclerView.
        setupAdapterClickListener()
    }

    private fun parseParams() {
        if (args.historyPhoto != null) {
            lifeLineViewModel.setSelectedHistoryPhoto(args.historyPhoto!!)
        }
    }

    /**
     * Установка слушателей кликов для обычного нажатия для адаптера в recyclerView
     * (фокусировка на карте).
     *
     */
    private fun setupAdapterClickListener() {
        with(photoListAdapter) {
            onPhotoClickListener = { photo, _ ->
                if (photo.latitude != null && photo.longitude != null) {
                    binding.mapView.mapWindow.map.move(
                        CameraPosition(
                            Point(photo.latitude, photo.longitude),
                            17.0f,
                            150.0f,
                            30.0f
                        )
                    )
                    // Узнаем, что точка не была добавлена, тогда добавляем ее на карту и во viewModel.
                    if (!placemarkIsAdded(photo)) {
                        addPlacemark(binding.mapView, photo)
                    }
                }
            }
        }
    }

    /**
     * Восстановление меток из viewModel.
     *
     */
    private fun restorePlacemarks() {
        lifeLineViewModel.placemarkPhotos.value?.let { placemarks ->
            placemarks.forEach {
                if (it.key.latitude != null && it.key.longitude != null) {
                    addPlacemark(binding.mapView, it.key)
                }
            }
        }
    }

    /**
     * Определение того, что точка уже добавлена на карту.
     *
     * @param photo - фото
     * @return - признак того, что точка добавлена
     */
    private fun placemarkIsAdded(photo: Photo): Boolean {
        return lifeLineViewModel.placemarkPhotos.value?.containsKey(photo) ?: false
    }

    /**
     * Добавление точки на карте + добавление во viewModel.
     *
     * @param mapView - карта
     * @param photo - фото
     */
    private fun addPlacemark(mapView: MapView, photo: Photo) {
        val imageProvider = ImageProvider.fromResource(context, R.drawable.photo_placemark)
        mapView.mapWindow.map.mapObjects.addPlacemark().apply {
            geometry = Point(photo.latitude!!, photo.longitude!!)
            setIcon(imageProvider)
            addPlacemarkViewModel(photo, this)
        }
    }

    /**
     * Добавление маркера во viewModel.
     *
     * @param photo - фото
     * @param placemarkMapObject - маркер на карте
     */
    private fun addPlacemarkViewModel(photo: Photo, placemarkMapObject: PlacemarkMapObject) {
        lifeLineViewModel.addPlacemark(photo, placemarkMapObject)
    }
}