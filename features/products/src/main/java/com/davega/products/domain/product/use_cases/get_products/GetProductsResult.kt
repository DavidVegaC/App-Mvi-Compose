package com.davega.products.domain.product.use_cases.get_products

import com.davega.products.domain.product.entities.Product

sealed class GetProductsResult {
    class Success(
        val products: List<Product>
    ): GetProductsResult()
    object Error: GetProductsResult()
}