package com.genar.e_market.cart.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.genar.e_market.productList.data.ProductEntity
import javax.inject.Singleton


@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
abstract class ProductDatabase : RoomDatabase() {

    @Singleton
    abstract fun cartProduct(): ProductDao
}