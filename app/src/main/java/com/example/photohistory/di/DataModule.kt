package com.example.photohistory.di

import android.content.Context
import com.example.photohistory.data.DatabaseMapper
import com.example.photohistory.data.PhotoHistoryDao
import com.example.photohistory.data.PhotoHistoryRepositoryImpl
import com.example.photohistory.data.UserRepositoryImpl
import com.example.photohistory.domain.repository.PhotoHistoryRepository
import com.example.photohistory.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.mapstruct.factory.Mappers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideUserRepository(@ApplicationContext context: Context): UserRepository {
        return UserRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun providePhotoHistoryRepository(
        photoHistoryDao: PhotoHistoryDao,
        mapper: DatabaseMapper
    ): PhotoHistoryRepository {
        return PhotoHistoryRepositoryImpl(photoHistoryDao, mapper)
    }

    @Provides
    @Singleton
    fun provideDatabaseMapper(): DatabaseMapper {
        return Mappers.getMapper(DatabaseMapper::class.java)
    }
}