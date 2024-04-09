package com.davega.products.domain.movement.use_cases.get_movements

import com.davega.products.domain.movement.entities.Movement

sealed class GetMovementsResult {

    data class Success(
        val movements: List<Movement>
    ): GetMovementsResult()

    object Error: GetMovementsResult()

}