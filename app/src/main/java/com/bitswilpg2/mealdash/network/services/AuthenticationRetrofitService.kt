package com.bitswilpg2.mealdash.network.services

import com.bitswilpg2.mealdash.network.models.CustomerDetails
import com.bitswilpg2.mealdash.network.models.LoginDetails
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * @Author Viswa Teja
 * @Date 9/29/2021 11:11 AM
 */
interface AuthenticationRetrofitService {

    @POST("registration/")
    suspend fun registerCustomer(@Body customerDetails: CustomerDetails): Response<ResponseBody>

    @POST("login/")
    suspend fun loginCustomer(@Body loginDetails: LoginDetails): Response<ResponseBody>

    companion object {
        var retrofitService: AuthenticationRetrofitService? = null
        fun getInstance() : AuthenticationRetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://192.168.0.105:8000/api/authentication/customer/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(AuthenticationRetrofitService::class.java)
            }
            return retrofitService!!
        }

    }
}