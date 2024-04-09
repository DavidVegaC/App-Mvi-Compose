package com.davega.products.ui.products

import com.davega.products.domain.product.use_cases.get_products.GetProductsResult
import com.davega.products.domain.product.use_cases.get_products.GetProductsUseCase
import com.davega.products.domain.product.use_cases.update_products.UpdateProductsResult
import com.davega.products.domain.product.use_cases.update_products.UpdateProductsUseCase
import com.davega.ui.lifecycle.StatefulViewModel
import com.davega.ui.utils.launch

class ProductsViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val updateProductsUseCase: UpdateProductsUseCase
): StatefulViewModel<ProductsUiState, ProductsUiEvent>(
    state = ProductsUiState()
) {

    override suspend fun onInit() {
        loadProducts()
    }

    fun loadProducts() = launch {
        if(uiState.products.isLoading){
            return@launch
        }
        setUiState {
            copy(
                products = products.toLoading()
            )
        }
        when(val result = getProductsUseCase()){
            is GetProductsResult.Success -> {
                setUiState {
                    copy(
                        products = products.toSuccess(
                            newValue = result.products
                        )
                    )
                }
            }
            is GetProductsResult.Error -> {
                setUiState {
                    copy(
                        products = products.toError("error")
                    )
                }
            }
        }
    }

    fun refreshProducts() = launch {
        if(uiState.products.isLoading){
            return@launch
        }
        setUiState {
            copy(
                products = products.toLoading()
            )
        }
        when(val result = updateProductsUseCase()){
            is UpdateProductsResult.Success -> {
                setUiState {
                    copy(
                        products = products.toSuccess(
                            newValue = result.products
                        )
                    )
                }
            }
            is UpdateProductsResult.Error -> {}
        }
    }

}