package com.davega.products.ui.detail

import com.davega.products.domain.movement.entities.Movement
import com.davega.products.domain.product.entities.Product
import com.davega.ui.utils.StatusValue

data class DetailUiState(
    val product: Product,
    val movements: StatusValue<List<Movement>> = StatusValue.None(listOf())
)