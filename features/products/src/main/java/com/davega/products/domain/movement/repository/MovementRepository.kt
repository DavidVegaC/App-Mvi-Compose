package com.davega.products.domain.movement.repository

import com.davega.domain.shared.utils.DataResult
import com.davega.products.domain.movement.entities.Movement

interface MovementRepository {

    suspend fun getMovements(id: String): DataResult<List<Movement>>

}