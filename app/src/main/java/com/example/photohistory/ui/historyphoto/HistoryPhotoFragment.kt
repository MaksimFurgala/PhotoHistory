package com.example.photohistory.ui.historyphoto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.photohistory.databinding.FragmentHistoryPhotoBinding
import com.example.photohistory.domain.models.HistoryPhoto
import com.example.photohistory.ui.historyphoto.HistoryPhotoFragmentDirections.actionNavHistoryPhotoToHistoryPhotoItemFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryPhotoFragment : Fragment() {

    private var _binding: FragmentHistoryPhotoBinding? = null
    private val binding get() = _binding!!

    val historyPhotoViewModel by viewModels<HistoryPhotoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = historyPhotoViewModel

            fabAddPhotoHistory.setOnClickListener {
                findNavController().navigate(
                    actionNavHistoryPhotoToHistoryPhotoItemFragment(
                        HistoryPhoto("", emptyList())
                    )
                )
            }
        }
    }
}