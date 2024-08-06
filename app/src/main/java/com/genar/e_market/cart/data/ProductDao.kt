package com.genar.e_market.cart.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.genar.e_market.productList.data.ProductEntity

@Dao
interface ProductDao {
    @Insert
    suspend fun insertProduct(product: ProductEntity)

    @Update
    suspend fun updateProduct(product: ProductEntity)

    @Delete
    suspend fun deleteProduct(product: ProductEntity)

    @Query("SELECT * FROM tbl_product")
    suspend fun getAll(): List<ProductEntity>

    @Query("SELECT * FROM tbl_product WHERE id = :id")
    suspend fun getById(id: String): ProductEntity?

}