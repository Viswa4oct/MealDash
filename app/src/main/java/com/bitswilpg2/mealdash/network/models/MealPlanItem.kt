package com.bitswilpg2.mealdash.network.models


import com.google.gson.annotations.SerializedName

data class MealPlanItem(
    @SerializedName("actual_price")
    val actualPrice: Double,
    val description: String,
    @SerializedName("final_price")
    val finalPrice: Double,
    val id: Int,
    val status: String,
    val type: String
)