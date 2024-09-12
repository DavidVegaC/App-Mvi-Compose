package com.davega.products.data.movements.repository

import com.davega.domain.shared.utils.DataResult
import com.davega.products.data.movements.remote.data_source.MovementRemoteDataSource
import com.davega.products.data.movements.repository.mappers.toMovement
import com.davega.products.domain.movement.entities.Movement
import com.davega.products.domain.movement.repository.MovementRepository
import javax.inject.Inject

class MovementRepositoryImpl @Inject constructor(
    private val remote: MovementRemoteDataSource
): MovementRepository {

    override suspend fun getMovements(id: String): DataResult<List<Movement>> {
        return remote.getMovements(id).map {
            it.map { movementResponse ->
                movementResponse.toMovement()
            }
        }
    }

}