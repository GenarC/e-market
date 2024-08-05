package com.genar.e_market.network

import com.genar.e_market.home.data.ProductEntity
import retrofit2.Response
import retrofit2.http.GET

interface ProductService {
    @GET("https://5fc9346b2af77700165ae514.mockapi.io/products")
    suspend fun getProductList(): Response<List<ProductEntity>>

    companion object {
        const val PRODUCT_END_POINT = "products"
    }
}