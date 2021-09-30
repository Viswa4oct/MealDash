package com.bitswilpg2.mealdash.network.models

data class CustomerDetails(
    var name: String,
    var email: String,
    var password: String,
    var phone: String,
    var address: String,
    var city: String,
    var pincode: String
)