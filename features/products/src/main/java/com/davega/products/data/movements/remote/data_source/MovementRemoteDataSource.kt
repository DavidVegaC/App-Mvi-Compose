package com.davega.products.data.movements.remote.data_source

import com.davega.data.shared.utils.safeApiCall
import com.davega.domain.shared.utils.DataResult
import com.davega.products.data.movements.remote.api.MovementApi
import com.davega.products.data.movements.remote.dto.MovementResponse
import javax.inject.Inject

class MovementRemoteDataSource @Inject constructor(
    private val api: MovementApi
) {

    suspend fun getMovements(id: String): DataResult<List<MovementResponse>> {
        return safeApiCall {
            api.getMovements(id)
        }
    }

}