package com.genar.e_market.cart.domain

import com.genar.e_market.cart.data.ProductRepository
import javax.inject.Inject

class GetProductsCartUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke() = productRepository.getProducts()
}