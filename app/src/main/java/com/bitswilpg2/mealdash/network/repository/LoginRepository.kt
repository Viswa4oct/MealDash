package com.bitswilpg2.mealdash.network.repository

import com.bitswilpg2.mealdash.network.models.LoginDetails
import com.bitswilpg2.mealdash.network.services.AuthenticationRetrofitService

/**
 * @Author Viswa Teja
 * @Date 9/29/2021 5:57 PM
 */
class LoginRepository constructor(private val authenticationRetrofitService: AuthenticationRetrofitService) {

    suspend fun loginCustomers(loginDetails: LoginDetails) = authenticationRetrofitService.loginCustomer(loginDetails)
}