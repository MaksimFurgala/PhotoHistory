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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment() {
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    val galleryViewModel by viewModels<GalleryViewModel>()

    private lateinit var contractImageCapture: ActivityResultLauncher<Uri>

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Регистрируем новый контракт Камера и инициализируем обработчик результата.
        contractImageCapture = registerForActivityResult(ActivityResultContracts.TakePicture()) {
            if (it) {
                binding.ivNewPhoto.setImageURI(galleryViewModel.newImageUri.value)
            } else {
                // В случае отмены логируем и удаляем временный файл.
                Log.d("GalleryFragment", "ImageCapture is cancelled.")
                galleryViewModel.newImageFile.value?.delete()
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}