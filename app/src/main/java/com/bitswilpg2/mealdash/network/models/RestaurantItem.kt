package com.bitswilpg2.mealdash.network.models


import com.google.gson.annotations.SerializedName

data class RestaurantItem(
    val address: String,
    val email: String,
    val id: Int,
    val latitude: String,
    val longitude: String,
    val name: String,
    val password: String,
    val phone: String,
    val timestamp: String
)