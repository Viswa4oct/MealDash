package com.bitswilpg2.mealdash.viewmodels

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bitswilpg2.mealdash.network.models.Restaurant
import com.bitswilpg2.mealdash.network.models.RestaurantItem
import com.bitswilpg2.mealdash.network.repository.RestaurantRepository
import kotlinx.coroutines.*
import java.util.*

/**
 * @Author Viswa Teja
 * @Date 9/29/2021 4:20 PM
 */
class RestaurantViewModel(
    private val restaurantRepository: RestaurantRepository,
    private var locationManager: LocationManager
    ): ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val restaurant = MutableLiveData<Restaurant>()
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    var job: Job? = null
    val loading = MutableLiveData<Boolean>()

    fun getRestaurantList() {
        fetchCityUsingGPSData()

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = restaurantRepository.getRestaurantList()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    restaurant.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    @SuppressLint("MissingPermission")
    fun fetchCityUsingGPSData() {
        val location: Location =
            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)!!
        this.latitude = location.latitude
        this.longitude = location.longitude
    }
}