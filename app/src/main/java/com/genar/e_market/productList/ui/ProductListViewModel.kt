package com.genar.e_market.productList.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.genar.e_market.cart.domain.AddItemToCartUseCase
import com.genar.e_market.di.DispatcherModule
import com.genar.e_market.productList.data.ProductEntity
import com.genar.e_market.productList.data.ProductRepository
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
class ProductListViewModel @Inject constructor(
    @DispatcherModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private var productUIMapper: ProductUIMapper,
    private val productRepository: ProductRepository,
    private val addToCartUseCase: AddItemToCartUseCase
) : ViewModel() {

    private val productCountLimit = 4
    private var currentPage = 0

    private val wholeProductList: ArrayList<ProductUIModel> = arrayListOf()
    private var currentProducList: ArrayList<ProductUIModel> = arrayListOf()

    private val _productList = MutableSharedFlow<List<ProductUIModel>?>()
    val productList: SharedFlow<List<ProductUIModel>?>
        get() = _productList.asSharedFlow()

    fun getProductList() {
        viewModelScope.launch(ioDispatcher) {
            val result = productRepository.getProductList().body()
            result?.map { productEntity ->
                val product = productUIMapper.map(productEntity)
                wholeProductList.add(product)
                currentProducList.add(product)
            }
            _productList.emit(
                currentProducList.toList()
                    .subList(currentPage * productCountLimit, productCountLimit)
            )
            currentPage++
        }
    }

    fun loadNextData(): List<ProductUIModel> {
        val result = currentProducList.subList(
            currentPage * productCountLimit,
            (currentPage + 1) * productCountLimit
        )

        currentPage++

        return result
    }

    fun searchProductList(query: String): List<ProductUIModel> {
        currentProducList = wholeProductList.filter { product ->
            product.name.contains(query, ignoreCase = true)
        }.toCollection(ArrayList())

        currentPage = 0

        return currentProducList.subList(
            0,
            if (currentProducList.size < productCountLimit)
                currentProducList.size
            else
                productCountLimit
        )
    }

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
                    createdAt = product.createdAt,
                    count = product.count
                )
            )
        }
    }
}