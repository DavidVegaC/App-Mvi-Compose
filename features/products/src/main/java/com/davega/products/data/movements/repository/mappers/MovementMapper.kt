package com.davega.products.data.movements.repository.mappers

import com.davega.products.data.movements.remote.dto.MovementResponse
import com.davega.products.domain.movement.entities.Movement
import com.davega.products.domain.movement.entities.MovementType
import com.davega.products.domain.product.entities.Currency
import java.util.*

fun MovementResponse.toMovement(): Movement {
    return Movement(
        id = id,
        description = description,
        type = MovementType.find(type),
        amount = amount,
        currency = Currency.find(currency),
        date = Date(date)
    )
}