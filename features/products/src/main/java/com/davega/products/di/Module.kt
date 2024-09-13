package com.davega.products.di

import com.davega.data.BuildConfig
import com.davega.data.shared.interceptors.AuthInterceptor
import com.davega.data.shared.interceptors.PostmanInterceptor
import com.davega.data.shared.utils.ApiBuilder
import com.davega.products.data.movements.remote.api.MovementApi
import com.davega.products.data.movements.repository.MovementRepositoryImpl
import com.davega.products.data.product.remote.api.ProductApi
import com.davega.products.data.product.repository.ProductRepositoryImpl
import com.davega.products.domain.movement.repository.MovementRepository
import com.davega.products.domain.product.repository.ProductRepository
import dagger.Binds
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

    @Singleton
    @Provides
    fun provideMovementRestApi(authInterceptor: AuthInterceptor): MovementApi = ApiBuilder(BuildConfig.BASE_URL)
        .addInterceptor(authInterceptor)
        .addInterceptor(PostmanInterceptor())
        .build<MovementApi>()
}

@Module
@InstallIn(SingletonComponent::class)
interface ProductsBindModule {

    @Binds
    fun bindMovementRepository(repository: MovementRepositoryImpl): MovementRepository

    @Binds
    fun bindProductsRepository(repository: ProductRepositoryImpl): ProductRepository

}