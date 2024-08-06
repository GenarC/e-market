package com.genar.e_market.cart.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.genar.e_market.cart.domain.AddItemToCartUseCase
import com.genar.e_market.cart.domain.GetProductsCartUseCase
import com.genar.e_market.cart.domain.RemoveItemFromCartUseCase
import com.genar.e_market.di.DispatcherModule
import com.genar.e_market.productList.data.ProductEntity
import com.genar.e_market.productList.mapper.ProductUIMapper
import com.genar.e_market.productList.model.ProductUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CartViewModel @Inject constructor(
    @DispatcherModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val productUIMapper: ProductUIMapper,
    private val getProductsCartUseCase: GetProductsCartUseCase,
    private val addItemToCartUseCase: AddItemToCartUseCase,
    private val removeItemFromCartUseCase: RemoveItemFromCartUseCase
) : ViewModel() {

    private val _cartProductList = MutableSharedFlow<List<ProductUIModel>?>()
    val cartProductList: SharedFlow<List<ProductUIModel>?>
        get() = _cartProductList.asSharedFlow()

    fun getProductsFromCart(){
        viewModelScope.launch(ioDispatcher) {
            val result = getProductsCartUseCase().map{
                productUIMapper.map(it)
            }
            _cartProductList.emit(result)
        }
    }

    fun addItem(
        product: ProductUIModel
    ){
        viewModelScope.launch(ioDispatcher) {
            addItemToCartUseCase(
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

    fun removeItem(
        product: ProductUIModel
    ){
        viewModelScope.launch(ioDispatcher) {
            removeItemFromCartUseCase(
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