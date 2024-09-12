package com.davega.data.shared.module

import android.content.Context
import com.davega.data.auth.local.data_source.AuthLocalDataSource
import com.davega.data.auth.utils.SessionTimer
import com.davega.data.shared.interceptors.AuthInterceptor
import com.davega.data.shared.persistence.data_storage.DataStorage
import com.davega.data.shared.persistence.data_storage.DataStorageImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Singleton
    @Provides
    fun provideDataStorage(@ApplicationContext appContext: Context): DataStorage = DataStorageImpl(appContext)

    @Singleton
    @Provides
    fun provideLocalDataSource(localStorage: DataStorage): AuthLocalDataSource = AuthLocalDataSource(localStorage)

    @Singleton
    @Provides
    fun provideAuthInterceptor(localDataSource: AuthLocalDataSource): Interceptor = AuthInterceptor(localDataSource)

    @Singleton
    @Provides
    fun provideSessionTimer(localDataSource: AuthLocalDataSource): SessionTimer = SessionTimer(localDataSource)
}
