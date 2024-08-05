package com.genar.e_market.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.genar.e_market.di.DispatcherModule
import com.genar.e_market.home.data.ProductRepository
import com.genar.e_market.home.mapper.ProductUIMapper
import com.genar.e_market.home.model.ProductUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @DispatcherModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private var productUIMapper: ProductUIMapper,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val productCountLimit = 12
    private var currentPage = 0

    private val wholeProductList: ArrayList<ProductUIModel> = arrayListOf()

    private val _productList = MutableSharedFlow<List<ProductUIModel>?>()
    val productList: SharedFlow<List<ProductUIModel>?>
        get() = _productList.asSharedFlow()

    fun getProductList() {
        viewModelScope.launch(ioDispatcher) {
            val result = productRepository.getProductList().body()
            result?.map { productEntity ->
                wholeProductList.add(productUIMapper.map(productEntity))
            }
            _productList.emit(
                wholeProductList.toList()
                    .subList(currentPage * productCountLimit, productCountLimit)
            )
            currentPage++
        }
    }

    fun loadNextData(): List<ProductUIModel> {
        val result = wholeProductList.subList(
            currentPage * productCountLimit,
            (currentPage + 1) * productCountLimit
        )

        currentPage++

        return result
    }

}