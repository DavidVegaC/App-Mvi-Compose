package com.davega.products.ui.products

import com.davega.products.domain.product.entities.Product
import com.davega.ui.utils.StatusValue

data class ProductsUiState(
    val products: StatusValue<List<Product>> = StatusValue.None(value = listOf())
)