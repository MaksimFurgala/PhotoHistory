package com.example.photohistory.di

import android.app.Application
import com.example.photohistory.data.AppDatabase
import com.example.photohistory.data.PhotoHistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDB(application: Application): AppDatabase {
        return AppDatabase.getInstance(application)
    }

    @Singleton
    @Provides
    fun provideDao(appDB: AppDatabase): PhotoHistoryDao {
        return appDB.photoHistoryDao()
    }
}