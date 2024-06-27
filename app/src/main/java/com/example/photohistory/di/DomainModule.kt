package com.example.photohistory.di

import com.example.photohistory.domain.repository.PhotoHistoryRepository
import com.example.photohistory.domain.repository.UserRepository
import com.example.photohistory.domain.usecases.PhotoUseCase
import com.example.photohistory.domain.usecases.SettingsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    @Singleton
    fun provideSettingsUseCase(repository: UserRepository): SettingsUseCase {
        return SettingsUseCase(repository)
    }

    @Provides
    @Singleton
    fun providePhotoUseCase(repository: PhotoHistoryRepository): PhotoUseCase {
        return PhotoUseCase(repository)
    }
}