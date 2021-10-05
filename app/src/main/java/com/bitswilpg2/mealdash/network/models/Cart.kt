package com.bitswilpg2.mealdash.network.models


import com.google.gson.annotations.SerializedName

data class Cart(
    @SerializedName("actual_price")
    var actualPrice: Double,
    var description: String,
    @SerializedName("final_price")
    var finalPrice: Double,
    @SerializedName("items")
    var cartItems: List<CartItem>,
    var status: String,
    var type: String
)