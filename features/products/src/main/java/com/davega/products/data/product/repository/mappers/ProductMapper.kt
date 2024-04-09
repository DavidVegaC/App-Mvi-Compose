package com.davega.products.data.product.repository.mappers

import com.davega.products.data.product.remote.dto.ProductResponse
import com.davega.products.domain.product.entities.Currency
import com.davega.products.domain.product.entities.Product

fun ProductResponse.toProduct(): Product {
    return Product(
        id = id,
        name = name,
        currency = Currency.find(currency),
        amount = amount,
        numberAccount = numberAccount
    )
}