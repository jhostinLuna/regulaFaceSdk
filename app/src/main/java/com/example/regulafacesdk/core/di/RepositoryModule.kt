package com.example.regulafacesdk.core.di

import com.example.regulafacesdk.data.ImageRepositoryImpl
import com.example.regulafacesdk.domain.ImageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BindRepositoryModule {
    @Binds
    abstract fun getImageRepository(imageRepositoryImpl: ImageRepositoryImpl): ImageRepository
}