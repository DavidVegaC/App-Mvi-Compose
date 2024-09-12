package com.davega.products.domain.product.use_cases.update_products

import com.davega.domain.shared.base.SimpleUseCase
import com.davega.domain.shared.utils.DataResult
import com.davega.products.domain.product.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class UpdateProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
): SimpleUseCase.OnlyResult<UpdateProductsResult> {

    override suspend fun invoke(): UpdateProductsResult {
        return when(val result = productRepository.updateProducts()){
            is DataResult.Success -> {
                UpdateProductsResult.Success(
                    products = result.data
                )
            }
            is DataResult.Error -> {
                UpdateProductsResult.Error
            }
        }
    }

}