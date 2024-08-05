package com.genar.e_market.home.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ProductEntity(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("model")
    val model: String,
    @SerializedName("brand")
    val brand: String,
    @SerializedName("id")
    val id: String
)