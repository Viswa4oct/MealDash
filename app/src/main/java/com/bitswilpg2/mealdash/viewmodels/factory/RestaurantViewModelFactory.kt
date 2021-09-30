package com.bitswilpg2.mealdash.viewmodels.factory

import android.location.LocationManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bitswilpg2.mealdash.network.repository.RestaurantRepository
import com.bitswilpg2.mealdash.viewmodels.RestaurantViewModel

/**
 * @Author Viswa Teja
 * @Date 9/29/2021 4:27 PM
 */
class RestaurantViewModelFactory (
    private val restaurantRepository: RestaurantRepository,
    private val locationManager: LocationManager
    ): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(RestaurantViewModel::class.java)) {
            RestaurantViewModel(this.restaurantRepository, this.locationManager) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}