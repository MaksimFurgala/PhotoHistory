package com.example.photohistory.di

import com.example.photohistory.MainActivity
import com.example.photohistory.domain.usecases.HistoryPhotoUseCase
import com.example.photohistory.domain.usecases.LifeLineUseCase
import com.example.photohistory.domain.usecases.MetricsUseCase
import com.example.photohistory.domain.usecases.PhotoUseCase
import com.example.photohistory.domain.usecases.SettingsUseCase
import com.example.photohistory.ui.home.HomeFragment
import com.example.photohistory.ui.home.HomeViewModel
import dagger.Component

@Component(modules = [RepositoryModule::class])
interface AppComponent {
    fun inject(useCase: HistoryPhotoUseCase)
    fun inject(useCase: LifeLineUseCase)
    fun inject(useCase: MetricsUseCase)
    fun inject(useCase: PhotoUseCase)
    fun inject(useCase: SettingsUseCase)
    fun inject(viewModel: HomeViewModel)
}