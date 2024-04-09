package com.davega.products.domain.product.use_cases.update_products

import com.davega.products.domain.product.entities.Product

sealed class UpdateProductsResult {
    class Success(
        val products: List<Product>
    ): UpdateProductsResult()
    object Error: UpdateProductsResult()
}