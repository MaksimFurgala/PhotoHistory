package com.example.photohistory.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.photohistory.R
import com.example.photohistory.databinding.FragmentHomeBinding
import com.example.photohistory.domain.models.UserSettings
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        //region Загрузка/обновление пользовательских настроек.
        homeViewModel.launchUserData()

        // Флаг для проверке того, что нужно обновить настройки пользователя
        val needUpdateUserSettings =
            homeViewModel.userSettings.value?.firstLaunchAppIsElapsed == false
        homeViewModel.userSettings.observe(viewLifecycleOwner) {
            if (needUpdateUserSettings) {
                updateUserSettings(it)
            }
        }
        //endregion

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_nav_history_photo)
        }

        val firstLaunchAppElapsed = homeViewModel.userSettings.value?.firstLaunchAppIsElapsed ?: false

        // Проверяем, что первый запуск приложения и запускаем анимации и выводим доп. элементы.
        if (!firstLaunchAppElapsed) {

        }
    }

    /**
     * Обновление пользовательских настроек.
     *
     * @param userSettings
     */
    private fun updateUserSettings(userSettings: UserSettings) {
        homeViewModel.updateUserSettings(userSettings)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}