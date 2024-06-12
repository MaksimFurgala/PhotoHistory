package com.example.photohistory.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.photohistory.R
import com.example.photohistory.databinding.FragmentHomeBinding
import com.example.photohistory.domain.models.UserSettings
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        homeViewModel.launchUserData()
        homeViewModel.userSettings.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                updateUserSettings(it)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_nav_history_photo)
        }
    }

    /**
     * Обновление пользовательских настроек.
     *
     * @param userSettings
     */
    private suspend fun updateUserSettings(userSettings: UserSettings) {
        if (!userSettings.firstLaunchAppElapsed)
            homeViewModel.updateStatusLaunch()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}