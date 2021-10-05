package com.bitswilpg2.mealdash.network.repository

import com.bitswilpg2.mealdash.network.models.Cart
import com.bitswilpg2.mealdash.network.services.CoreRetrofitService

/**
 * @Author Viswa Teja
 * @Date 10/1/2021 6:22 PM
 */
class CartRepository constructor(private val coreRetrofitService: CoreRetrofitService) {

    suspend fun postCartDetails(cart: Cart, resID: Int) = coreRetrofitService.postCartDetails(cart/*, resID*/)

}