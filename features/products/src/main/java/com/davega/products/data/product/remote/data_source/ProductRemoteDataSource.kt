package com.davega.products.data.product.remote.data_source

import com.davega.data.shared.utils.safeApiCall
import com.davega.domain.shared.utils.DataResult
import com.davega.products.data.product.remote.api.ProductApi
import com.davega.products.data.product.remote.dto.ProductResponse

class ProductRemoteDataSource(
    private val api: ProductApi
) {

    suspend fun getProducts(): DataResult<List<ProductResponse>> {
        return safeApiCall {
            api.getProducts()
        }
    }

    suspend fun updateProducts(): DataResult<List<ProductResponse>> {
        return safeApiCall {
            api.updateProducts()
        }
    }

}