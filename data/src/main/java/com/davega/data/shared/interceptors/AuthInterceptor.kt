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

    private val emptyAuth: Auth by lazy {
        Auth(EMPTY_STRING, EMPTY_STRING)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val auth = runBlocking {
            local.getAuth() ?: emptyAuth
        }
        val request = chain.request().newBuilder()
            .addHeader(
                AUTHORIZATION,
                "${auth.type} ${auth.token}"
            )
            .build()
        return chain.proceed(request)
    }

    companion object {

        private const val AUTHORIZATION: String = "Authorization"
        private const val EMPTY_STRING: String = ""
    }
}