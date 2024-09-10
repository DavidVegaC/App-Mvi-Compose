package com.davega.auth.di

import com.davega.auth.data.auth.remote.api.AuthApi
import com.davega.auth.data.auth.remote.data_source.AuthRemoteDataSource
import com.davega.auth.data.auth.repository.AuthRepositoryImpl
import com.davega.auth.domain.auth.repository.AuthRepository
import com.davega.auth.domain.auth.use_cases.get_cipher.GetCipherUseCase
import com.davega.auth.domain.auth.use_cases.get_dni.GetDniUseCase
import com.davega.auth.domain.auth.use_cases.login.LoginUseCase
import com.davega.auth.domain.auth.use_cases.save_cipher.SaveCipherUseCase
import com.davega.auth.ui.login.LoginBaseViewModel
import com.davega.data.BuildConfig
import com.davega.data.shared.interceptors.PostmanInterceptor
import com.davega.data.shared.utils.ApiBuilder
import com.davega.ibkapp.utils.createModuleLoader
import org.koin.androidx.viewmodel.dsl.viewModel

internal val moduleLoader by createModuleLoader(
    uiModule = {
        viewModel {
            LoginBaseViewModel(
                loginUseCase = get(),
                getDniUseCase = get(),
                saveCipherUseCase = get(),
                getCipherUseCase = get()
            )
        }
    },
    domainModule = {
        single { LoginUseCase(get(), get()) }
        single { GetDniUseCase(get()) }
        single { SaveCipherUseCase(get()) }
        single { GetCipherUseCase(get()) }
    },
    dataModule = {
        single {
            ApiBuilder(BuildConfig.BASE_URL)
                .addInterceptor(PostmanInterceptor())
                .build<AuthApi>()
        }
        single { AuthRemoteDataSource(get()) }
        single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    }
)

fun loadModules() = moduleLoader