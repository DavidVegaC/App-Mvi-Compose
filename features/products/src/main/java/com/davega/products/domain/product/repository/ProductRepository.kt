package com.davega.products.domain.product.repository

import com.davega.domain.shared.utils.DataResult
import com.davega.products.domain.product.entities.Product

interface ProductRepository {

    suspend fun getProducts(): DataResult<List<Product>>

    suspend fun updateProducts(): DataResult<List<Product>>

}