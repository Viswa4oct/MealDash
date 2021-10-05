package com.bitswilpg2.mealdash.network.repository

import com.bitswilpg2.mealdash.network.services.CoreRetrofitService

/**
 * @Author Viswa Teja
 * @Date 10/1/2021 2:27 PM
 */
class FoodItemsRepository constructor(private val coreRetrofitService: CoreRetrofitService) {

    suspend fun getItemList() = coreRetrofitService.getItemList()

}