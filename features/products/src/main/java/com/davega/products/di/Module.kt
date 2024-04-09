package com.davega.products.di

import com.davega.data.BuildConfig
import com.davega.data.shared.interceptors.AuthInterceptor
import com.davega.data.shared.interceptors.PostmanInterceptor
import com.davega.data.shared.utils.ApiBuilder
import com.davega.ibkapp.utils.createModuleLoader
import com.davega.products.data.movements.remote.api.MovementApi
import com.davega.products.data.movements.remote.data_source.MovementRemoteDataSource
import com.davega.products.data.movements.repository.MovementRepositoryImpl
import com.davega.products.data.product.remote.api.ProductApi
import com.davega.products.data.product.remote.data_source.ProductRemoteDataSource
import com.davega.products.data.product.repository.ProductRepositoryImpl
import com.davega.products.domain.movement.repository.MovementRepository
import com.davega.products.domain.movement.use_cases.get_movements.GetMovementsUseCase
import com.davega.products.domain.product.repository.ProductRepository
import com.davega.products.domain.product.use_cases.get_products.GetProductsUseCase
import com.davega.products.domain.product.use_cases.update_products.UpdateProductsUseCase
import com.davega.products.ui.detail.DetailViewModel
import com.davega.products.ui.products.ProductsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

internal val moduleLoader by createModuleLoader(
    uiModule = {
        viewModel {
            ProductsViewModel(get(), get())
        }
        viewModel { parameters ->
            DetailViewModel(
                product = parameters[0],
                getMovementsUseCase = get()
            )
        }
    },
    domainModule = {
        single { GetProductsUseCase(get()) }
        single { UpdateProductsUseCase(get()) }
        single { GetMovementsUseCase(get()) }
    },
    dataModule = {
        single {
            ApiBuilder(BuildConfig.BASE_URL)
                .addInterceptor(PostmanInterceptor())
                .addInterceptor(get<AuthInterceptor>())
                .build<ProductApi>()
        }
        single { ProductRemoteDataSource(get()) }
        single<ProductRepository> { ProductRepositoryImpl(get()) }

        single {
            ApiBuilder(BuildConfig.BASE_URL)
                .addInterceptor(PostmanInterceptor())
                .addInterceptor(get<AuthInterceptor>())
                .build<MovementApi>()
        }
        single { MovementRemoteDataSource(get()) }
        single<MovementRepository> { MovementRepositoryImpl(get()) }
    }
)

fun loadModules() = moduleLoader