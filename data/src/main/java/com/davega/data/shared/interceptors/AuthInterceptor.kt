package com.davega.data.shared.interceptors

import com.davega.data.auth.local.data_source.AuthLocalDataSource
import com.davega.domain.auth.entities.Auth
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val local: AuthLocalDataSource
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val auth = runBlocking {
            local.getAuth() ?: Auth("", "")
        }
        val request = chain.request().newBuilder()
            .addHeader(
                "Authorization",
                "${auth.type} ${auth.token}"
            )
            .build()
        return chain.proceed(request)
    }

}