package com.davega.products.domain.product.entities

import android.net.Uri
import com.davega.products.ui.navigation.JsonNavType
import com.google.gson.Gson

data class Product(
    val id: String,
    val name: String,
    val currency: Currency,
    val amount: Double,
    val numberAccount: String
) {
    override fun toString(): String = Uri.encode(Gson().toJson(this))
}

class ProductArgType : JsonNavType<Product>() {
    override fun fromJsonParse(value: String): Product = Gson().fromJson(value, Product::class.java)

    override fun Product.getJsonParse(): String = Gson().toJson(this)
}