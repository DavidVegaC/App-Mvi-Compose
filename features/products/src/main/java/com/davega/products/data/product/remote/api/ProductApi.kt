package com.davega.products.data.product.remote.api

import com.davega.products.data.product.remote.dto.ProductResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProductApi {

    @GET("obtenerProductos")
    suspend fun getProducts(): Response<List<ProductResponse>>

    @GET("actualizarProductos")
    suspend fun updateProducts(): Response<List<ProductResponse>>

}