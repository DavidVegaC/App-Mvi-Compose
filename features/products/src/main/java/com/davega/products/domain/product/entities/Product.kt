package com.davega.products.domain.product.entities

data class Product(
    val id: String,
    val name: String,
    val currency: Currency,
    val amount: Double,
    val numberAccount: String
)