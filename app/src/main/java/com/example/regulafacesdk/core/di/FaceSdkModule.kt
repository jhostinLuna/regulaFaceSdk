package com.example.regulafacesdk.core.di

import com.example.regulafacesdk.data.FaceSdkRepositoryImpl
import com.example.regulafacesdk.domain.FaceSdkRepository
import com.regula.facesdk.FaceSDK
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FaceSdkModule {
    @Provides
    @Singleton
    fun provideFaceSdk(): FaceSDK {
        return FaceSDK.Instance()
    }

}

@Module
@InstallIn(SingletonComponent::class)
abstract class BindFaceSdkRepository {
    @Binds
    abstract fun bindFaceSdkRepository(faceSdkRepositoryImpl: FaceSdkRepositoryImpl): FaceSdkRepository
}