package com.genar.e_market.productList.mapper

import com.genar.e_market.productList.data.ProductEntity
import com.genar.e_market.productList.model.ProductUIModel
import javax.inject.Inject

class ProductUIMapper @Inject constructor() {
    fun map(productEntity: ProductEntity): ProductUIModel {
        return ProductUIModel(
            createdAt = productEntity.createdAt,
            name = productEntity.name,
            image = productEntity.image,
            price = productEntity.price,
            description = productEntity.description,
            model = productEntity.model,
            brand = productEntity.brand,
            id = productEntity.id,
            count = productEntity.count
        )
    }
}