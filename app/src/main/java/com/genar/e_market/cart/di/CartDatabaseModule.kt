package com.genar.e_market.cart.di

import android.content.Context
import androidx.room.Room
import com.genar.e_market.cart.data.ProductDao
import com.genar.e_market.cart.data.ProductDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CartDatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): ProductDatabase {
        return Room.databaseBuilder(
            context,
            ProductDatabase::class.java,
            PRODUCT_DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideCartProductDao(database: ProductDatabase): ProductDao {
        return database.cartProduct()
    }

    companion object {
        const val PRODUCT_DATABASE_NAME = "cart_product_database"
    }
}