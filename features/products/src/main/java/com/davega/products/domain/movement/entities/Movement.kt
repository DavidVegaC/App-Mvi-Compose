package com.davega.products.domain.movement.entities

import com.davega.products.domain.product.entities.Currency
import java.util.*

data class Movement(
    val id: String,
    val description: String,
    val type: MovementType,
    val amount: Double,
    val currency: Currency,
    val date: Date
)