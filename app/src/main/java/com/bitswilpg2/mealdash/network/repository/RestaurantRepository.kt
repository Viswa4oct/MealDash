package com.bitswilpg2.mealdash.network.repository

import com.bitswilpg2.mealdash.network.services.CoreRetrofitService

/**
 * @Author Viswa Teja
 * @Date 9/29/2021 4:25 PM
 */
class RestaurantRepository constructor(private val coreRetrofitService: CoreRetrofitService) {

    suspend fun getRestaurantList() = coreRetrofitService.getRestaurantList()

}