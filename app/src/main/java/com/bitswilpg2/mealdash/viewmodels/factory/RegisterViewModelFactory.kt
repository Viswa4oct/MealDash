package com.bitswilpg2.mealdash.viewmodels.factory

import android.content.Context
import android.location.LocationManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bitswilpg2.mealdash.network.models.CustomerDetails
import com.bitswilpg2.mealdash.network.repository.RegisterRepository
import com.bitswilpg2.mealdash.viewmodels.RegisterViewModel

/**
 * @Author Viswa Teja
 * @Date 9/29/2021 12:18 PM
 */
class RegisterViewModelFactory(
    private val registerRepository: RegisterRepository,
    private val customerDetails: CustomerDetails,
    private val context: Context,
    private val locationManager: LocationManager
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            RegisterViewModel(this.registerRepository, this.customerDetails, this.context, this.locationManager) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}