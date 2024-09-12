package com.davega.auth.di

import com.davega.auth.data.auth.remote.api.AuthApi
import com.davega.data.BuildConfig
import com.davega.data.shared.interceptors.PostmanInterceptor
import com.davega.data.shared.utils.ApiBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AuthModel {

    @Singleton
    @Provides
    fun provideAppRestApi(): AuthApi = ApiBuilder(BuildConfig.BASE_URL).addInterceptor(PostmanInterceptor()).build<AuthApi>()
}