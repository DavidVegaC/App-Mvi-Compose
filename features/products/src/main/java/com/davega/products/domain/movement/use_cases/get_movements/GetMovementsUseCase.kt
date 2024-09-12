package com.davega.products.domain.movement.use_cases.get_movements

import com.davega.domain.shared.base.SimpleUseCase
import com.davega.domain.shared.utils.DataResult
import com.davega.products.domain.movement.repository.MovementRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class GetMovementsUseCase @Inject constructor(
    private val movementRepository: MovementRepository
): SimpleUseCase.ParamsAndResult<String, GetMovementsResult> {

    override suspend fun invoke(params: String): GetMovementsResult {
        return when(val result = movementRepository.getMovements(params)){
            is DataResult.Success -> {
                GetMovementsResult.Success(
                    movements = result.data
                )
            }
            is DataResult.Error -> {
                GetMovementsResult.Error
            }
        }
    }

}