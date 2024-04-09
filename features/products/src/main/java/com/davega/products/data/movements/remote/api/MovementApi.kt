package com.davega.products.data.movements.remote.api

import com.davega.products.data.movements.remote.dto.MovementResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovementApi {

    @GET("obtenerMovimientos/{id}")
    suspend fun getMovements(@Path("id") id: String): Response<List<MovementResponse>>

}