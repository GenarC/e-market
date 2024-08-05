package com.genar.e_market.home.mapper

import com.genar.e_market.home.data.ProductEntity
import com.genar.e_market.home.model.ProductUIModel
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
            id = productEntity.id
        )
    }
}