package com.example.photohistory.di

import com.example.photohistory.domain.repository.PhotoHistoryRepository
import com.example.photohistory.domain.repository.UserRepository
import com.example.photohistory.domain.usecases.PhotoUseCase
import com.example.photohistory.domain.usecases.SettingsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
class DomainModule {

    @Provides
    @ActivityRetainedScoped
    fun provideSettingsUseCase(repository: UserRepository): SettingsUseCase {
        return SettingsUseCase(repository)
    }

    @Provides
    @ActivityRetainedScoped
    fun providePhotoUseCase(repository: PhotoHistoryRepository): PhotoUseCase {
        return PhotoUseCase(repository)
    }
}