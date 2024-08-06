package com.genar.e_market.cart.domain

import com.genar.e_market.cart.data.ProductRepository
import com.genar.e_market.productList.data.ProductEntity
import javax.inject.Inject

class AddItemToCartUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(product: ProductEntity) {
        val item = productRepository.getProductById(product.id.toString())

        if (item == null) {
            productRepository.insertProduct(product)
        }else{
            val updatedItem = item.copy(count = item.count + 1)
            productRepository.updateProduct(updatedItem)
        }
    }
}