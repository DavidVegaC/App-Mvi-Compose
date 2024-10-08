package com.davega.products.domain.product.use_cases.get_products

import com.davega.domain.shared.base.SimpleUseCase
import com.davega.domain.shared.utils.DataResult
import com.davega.products.domain.product.repository.ProductRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
): SimpleUseCase.OnlyResult<GetProductsResult> {

    override suspend fun invoke(): GetProductsResult {
        return when(val result = productRepository.getProducts()){
            is DataResult.Success -> {
                GetProductsResult.Success(
                    products = result.data
                )
            }
            is DataResult.Error -> {
                GetProductsResult.Error
            }
        }
    }

}