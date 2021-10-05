package com.bitswilpg2.mealdash.network.repository

import com.bitswilpg2.mealdash.network.services.CoreRetrofitService

/**
 * @Author Viswa Teja
 * @Date 9/30/2021 5:52 PM
 */
class MealPlanRepository constructor(private val coreRetrofitService: CoreRetrofitService) {

    suspend fun getMealPlanList() = coreRetrofitService.getMealPlanList()

}