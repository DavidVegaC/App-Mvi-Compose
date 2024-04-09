package com.davega.products.data.product.remote.dto

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("numberAccount")
    val numberAccount: String
)