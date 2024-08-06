package com.genar.e_market.productDetail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.genar.e_market.cart.domain.AddItemToCartUseCase
import com.genar.e_market.di.DispatcherModule
import com.genar.e_market.productList.data.ProductEntity
import com.genar.e_market.productList.model.ProductUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    @DispatcherModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val addToCartUseCase: AddItemToCartUseCase
) : ViewModel() {

    fun addItemToCart(product: ProductUIModel) {
        viewModelScope.launch(ioDispatcher) {
            addToCartUseCase(
                ProductEntity(
                    id = product.id,
                    name = product.name,
                    price = product.price,
                    description = product.description,
                    image = product.image,
                    brand = product.brand,
                    model = product.model,
                    createdAt = product.createdAt
                )
            )
        }
    }
}