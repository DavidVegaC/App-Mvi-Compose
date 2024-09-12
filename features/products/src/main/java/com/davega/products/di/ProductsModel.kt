package com.davega.products.di
import com.davega.data.BuildConfig
import com.davega.data.shared.interceptors.AuthInterceptor
import com.davega.data.shared.interceptors.PostmanInterceptor
import com.davega.data.shared.utils.ApiBuilder
import com.davega.products.data.product.remote.api.ProductApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ProductsModel {

    @Singleton
    @Provides
    fun provideAppRestApi(authInterceptor: AuthInterceptor): ProductApi = ApiBuilder(BuildConfig.BASE_URL)
        .addInterceptor(authInterceptor)
        .addInterceptor(PostmanInterceptor())
        .build<ProductApi>()
}