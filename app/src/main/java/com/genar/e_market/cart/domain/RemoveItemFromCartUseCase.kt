package com.genar.e_market.cart.domain

import com.genar.e_market.cart.data.ProductRepository
import com.genar.e_market.productList.data.ProductEntity
import javax.inject.Inject

class RemoveItemFromCartUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(product: ProductEntity) {
        val item = productRepository.getProductById(product.id.toString())

        if (item != null && item.count > 1) {
            val updatedItem = item.copy(count = item.count - 1)
            productRepository.updateProduct(updatedItem)
        } else if (item != null && item.count == 1) {
            productRepository.deleteProduct(item)
        }
    }
}