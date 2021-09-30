package com.bitswilpg2.mealdash.network.services

import com.bitswilpg2.mealdash.network.models.Restaurant
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * @Author Viswa Teja
 * @Date 9/29/2021 3:43 PM
 */
interface CoreRetrofitService {

    @GET("getRestaurantList")
    suspend fun getRestaurantList() : Response<Restaurant>

    companion object {
        var retrofitService: CoreRetrofitService? = null
        fun getInstance() : CoreRetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://192.168.0.105:8000/api/core/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(CoreRetrofitService::class.java)
            }
            return retrofitService!!
        }

    }
}