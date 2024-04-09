package com.davega.products.data.movements.remote.dto

import com.google.gson.annotations.SerializedName

data class MovementResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("date")
    val date: Long
)