package com.davega.data.shared.utils

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val httpLoggingInterceptor by lazy {
    HttpLoggingInterceptor().apply {
        level = if(BuildConfig.DEBUG){
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }
}

class ApiBuilder(
    val baseUrl: String
) {

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)

    fun addInterceptor(interceptor: Interceptor): ApiBuilder {
        okHttpClient.addInterceptor(interceptor)
        return this
    }

    inline fun <reified T> build(): T {
        return Retrofit.Builder()
            .client(okHttpClient.build())
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(T::class.java)
    }
}