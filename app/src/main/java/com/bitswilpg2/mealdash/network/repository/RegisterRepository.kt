package com.bitswilpg2.mealdash.network.repository

import com.bitswilpg2.mealdash.network.models.CustomerDetails
import com.bitswilpg2.mealdash.network.services.AuthenticationRetrofitService

/**
 * @Author Viswa Teja
 * @Date 9/29/2021 11:46 AM
 */
class RegisterRepository constructor(private val authenticationRetrofitService: AuthenticationRetrofitService) {

    suspend fun registerCustomers(customerDetails: CustomerDetails) = authenticationRetrofitService.registerCustomer(customerDetails)
}