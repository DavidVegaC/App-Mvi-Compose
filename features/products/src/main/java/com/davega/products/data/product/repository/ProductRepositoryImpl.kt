package com.davega.products.data.product.repository

import com.davega.domain.shared.utils.DataResult
import com.davega.products.data.product.remote.data_source.ProductRemoteDataSource
import com.davega.products.data.product.repository.mappers.toProduct
import com.davega.products.domain.product.entities.Product
import com.davega.products.domain.product.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val remote: ProductRemoteDataSource
): ProductRepository {

    override suspend fun getProducts(): DataResult<List<Product>> {
        return remote.getProducts().map {
            it.map { productResponse -> productResponse.toProduct() }
        }
    }

    override suspend fun updateProducts(): DataResult<List<Product>> {
        return remote.updateProducts().map {
            it.map { productResponse -> productResponse.toProduct() }
        }
    }

}