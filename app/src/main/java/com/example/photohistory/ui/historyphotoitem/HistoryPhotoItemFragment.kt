package com.example.photohistory.ui.historyphotoitem

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.photohistory.databinding.FragmentHistoryPhotoItemBinding
import com.example.photohistory.domain.models.GalleryMode
import com.example.photohistory.domain.models.HistoryPhoto.Companion.UNDEFINED_ID
import com.example.photohistory.domain.models.ScreenMode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryPhotoItemFragment : Fragment() {

    private var _binding: FragmentHistoryPhotoItemBinding? = null
    private val binding get() = _binding!!

    lateinit var screenMode: ScreenMode
    private val args by navArgs<HistoryPhotoItemFragmentArgs>()

    val historyPhotoItemViewModel by viewModels<HistoryPhotoItemViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryPhotoItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = historyPhotoItemViewModel
        }

        addTextChangeListeners()
        setOnClickListenerSelectPhotos()
        launchRightMode()
    }

    /**
     * Добавление обработчика для контрола Наименование.
     *
     */
    private fun addTextChangeListeners() {
        with(binding) {
            etName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    historyPhotoItemViewModel.resetErrorInputName()
                }

                override fun afterTextChanged(p0: Editable?) {
                }

            })
        }
    }

    /**
     * Запуск режима добавления новой фото-истории.
     *
     */
    private fun launchAddMode() {
        with(binding) {
            btnSave.setOnClickListener {
                val addResult = historyPhotoItemViewModel.addHistoryPhoto(
                    etName.text.toString(),
                    historyPhotoItemViewModel.selectedPhotos.value ?: emptyList()
                )
                if (addResult) {
                    findNavController().navigate(HistoryPhotoItemFragmentDirections.actionHistoryPhotoItemFragmentToNavHistoryPhoto())
                }

                // если нет выбранных фотографий, то выводим Toast
                if (historyPhotoItemViewModel.selectedPhotos.value?.any() == false) {
                    Toast.makeText(
                        context,
                        "Выберите как минимум одну фотографию",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    /**
     * Запуск режима редактирования фото-истории.
     *
     */
    private fun launchEditMode() {
        with(binding) {
            btnSave.setOnClickListener {
                historyPhotoItemViewModel.editHistoryPhoto(
                    etName.text.toString(),
                    historyPhotoItemViewModel.selectedPhotos.value ?: emptyList()
                )
            }
        }
    }

    /**
     * Добавления обработчика клика для выбора фото: навигация к фрагменту с выбором фото
     * и обновление тек. фото-истории
     *
     */
    private fun setOnClickListenerSelectPhotos() {
        with(binding) {
            btnSelectPhotos.setOnClickListener {
                val historyPhoto = historyPhotoItemViewModel.currentHistoryPhoto.value
                if (historyPhoto != null) {
                    historyPhotoItemViewModel.updateCurrentHistoryPhoto(historyPhoto.copy(name = binding.etName.text.toString()))
                }
                findNavController().navigate(
                    HistoryPhotoItemFragmentDirections.actionHistoryPhotoItemFragmentToNavGallery(
                        historyPhotoItemViewModel.currentHistoryPhoto.value,
                        GalleryMode.SELECT
                    )
                )
            }
        }
    }

    /**
     * Парсинг аргументов из текущего фрагмента, которые переданы из activity или фрагмента.
     *
     */
    private fun parseParams() {
        if (args.historyPhoto?.historyPhotoId != UNDEFINED_ID) {
            screenMode = ScreenMode.EDIT
        } else {
            screenMode = ScreenMode.ADD
        }
        historyPhotoItemViewModel.updateCurrentHistoryPhoto(args.historyPhoto!!)
    }

    /**
     * Запуск режима для фрагмента по добавлению/редактированию фото-истории.
     *
     */
    private fun launchRightMode() {
        when (screenMode) {
            ScreenMode.EDIT -> launchEditMode()
            ScreenMode.ADD -> {
                launchAddMode()
                binding.etName.setText(historyPhotoItemViewModel.currentHistoryPhoto.value?.name)
            }
        }
    }

}