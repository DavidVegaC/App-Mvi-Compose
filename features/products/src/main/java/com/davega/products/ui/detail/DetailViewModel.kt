package com.davega.products.ui.detail

import com.davega.products.domain.movement.use_cases.get_movements.GetMovementsResult
import com.davega.products.domain.movement.use_cases.get_movements.GetMovementsUseCase
import com.davega.products.domain.product.entities.Product
import com.davega.ui.lifecycle.StatefulViewModel
import com.davega.ui.utils.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    product: Product,
    private val getMovementsUseCase: GetMovementsUseCase
): StatefulViewModel<DetailUiState, DetailUiEvent>(
    state = DetailUiState(
        product = product
    )
){

    override suspend fun onInit() {
        loadMovements()
    }

    fun loadMovements() = launch {
        setUiState {
            copy(
                movements = movements.toLoading()
            )
        }
        when(val result = getMovementsUseCase(uiState.product.id)){
            is GetMovementsResult.Success -> {
                setUiState {
                    copy(
                        movements = movements.toSuccess(
                            newValue = result.movements
                        )
                    )
                }
            }
            is GetMovementsResult.Error -> {
                setUiState {
                    copy(
                        movements = movements.toError("error")
                    )
                }
            }
        }
    }

}