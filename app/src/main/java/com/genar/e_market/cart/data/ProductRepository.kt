package com.genar.e_market.cart.data

import com.genar.e_market.productList.data.ProductEntity
import javax.inject.Inject


class ProductRepository @Inject constructor(
    private val productDao: ProductDao
) {

    suspend fun insertProduct(productEntity: ProductEntity) =
        productDao.insertProduct(productEntity)

    suspend fun deleteProduct(productEntity: ProductEntity) =
        productDao.deleteProduct(productEntity)

    suspend fun getProducts() = productDao.getAll()

    suspend fun getProductById(id: String) = productDao.getById(id)

    suspend fun updateProduct(productEntity: ProductEntity) =
        productDao.updateProduct(productEntity)
}