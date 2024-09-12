package com.davega.auth.data.remote.api

import com.davega.auth.data.remote.dto.request.AuthRequest
import com.davega.auth.data.remote.dto.response.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("iniciarSesion")
    suspend fun login(@Body request: AuthRequest): Response<AuthResponse>

}