package com.example.photohistory.ui.historyphotoitem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.photohistory.databinding.FragmentHistoryPhotoItemBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryPhotoItemFragment : Fragment() {

    private var _binding: FragmentHistoryPhotoItemBinding? = null
    private val binding get() = _binding!!

    val historyPhotoItemViewModel by viewModels<HistoryPhotoItemViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
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
    }
}