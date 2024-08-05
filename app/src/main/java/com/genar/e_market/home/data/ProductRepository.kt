package com.genar.e_market.home.data

import com.genar.e_market.network.ProductService
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productService: ProductService
) {
    suspend fun getProductList() = productService.getProductList()
}