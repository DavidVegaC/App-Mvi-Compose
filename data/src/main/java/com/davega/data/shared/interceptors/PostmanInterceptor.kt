package com.davega.data.shared.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class PostmanInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("x-mock-match-request-body", "true")
            .build()
        return chain.proceed(request)
    }

}