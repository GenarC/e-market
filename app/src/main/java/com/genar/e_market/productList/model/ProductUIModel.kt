package com.genar.e_market.productList.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ProductUIModel(
    val createdAt: String,
    val name: String,
    val image: String,
    val price: Double,
    val description: String,
    val model: String,
    val brand: String,
    val id: Int,
    val count: Int = 0
) : Parcelable