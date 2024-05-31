package com.example.photohistory.di

import com.example.photohistory.data.PhotoHistoryRepositoryImpl
import com.example.photohistory.domain.repository.PhotoHistoryRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface RepositoryModule {
    @Binds
    fun providePhotoHistoryRepository(repository: PhotoHistoryRepositoryImpl): PhotoHistoryRepository
}