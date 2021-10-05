package com.bitswilpg2.mealdash.network.services

import com.bitswilpg2.mealdash.network.models.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * @Author Viswa Teja
 * @Date 9/29/2021 3:43 PM
 */
interface CoreRetrofitService {

    @GET("getRestaurantList")
    suspend fun getRestaurantList() : Response<Restaurant>

    @GET("getMealPlanList/")
    suspend fun getMealPlanList() : Response<MealPlan>

    @GET("getItemList/")
    suspend fun getItemList() : Response<Item>

    @POST("1/mealplan")
    suspend fun postCartDetails(@Body cart: Cart/*, @Path("restaurantId") resID: Int*/): Response<ResponseBody>

    companion object {
        var retrofitService: CoreRetrofitService? = null
        var resService: CoreRetrofitService? = null
        fun getInstance() : CoreRetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
//                    .baseUrl("http://192.168.0.105:8000/api/core/")
                    .baseUrl("http://44.195.199.28:8000/api/core/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(CoreRetrofitService::class.java)
            }
            return retrofitService!!
        }

        fun getResInstance() : CoreRetrofitService {
            if (resService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://44.195.199.28:8000/api/restaurant/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                resService = retrofit.create(CoreRetrofitService::class.java)
            }
            return resService!!
        }

    }
}