package com.davega.data.shared.module

import com.davega.data.auth.local.data_source.AuthLocalDataSource
import com.davega.data.auth.utils.SessionTimer
import com.davega.data.shared.interceptors.AuthInterceptor
import com.davega.data.shared.persistence.data_storage.DataStorage
import com.davega.data.shared.persistence.data_storage.DataStorageImpl
import org.koin.dsl.module

val dataModule = module {
    single<DataStorage> { DataStorageImpl(get()) }
    single { AuthLocalDataSource(get()) }
    single { AuthInterceptor(get()) }
    single { SessionTimer(get()) }
}
