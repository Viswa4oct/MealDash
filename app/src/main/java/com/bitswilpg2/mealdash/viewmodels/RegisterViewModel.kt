package com.bitswilpg2.mealdash.viewmodels

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bitswilpg2.mealdash.network.models.CustomerDetails
import com.bitswilpg2.mealdash.network.repository.RegisterRepository
import kotlinx.coroutines.*
import java.util.*

/**
 * @Author Viswa Teja
 * @Date 9/29/2021 11:48 AM
 */
class RegisterViewModel(
    private val repository: RegisterRepository,
    private var customerDetails: CustomerDetails,
    private var context: Context,
    private var locationManager: LocationManager
)  : ViewModel() {

    var name = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var phone = MutableLiveData<String>()
    var address = MutableLiveData<String>()
    var city = MutableLiveData<String>()
    var pincode = MutableLiveData<String>()
    private val errorMessage = MutableLiveData<String>()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun registerCustomers() {
        setCustomerDetails()

        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.registerCustomers(customerDetails)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                } else {
                    onError("Registration Failed : ${response.message()} ")
                }
            }
        }
    }

    private fun setCustomerDetails() {
        customerDetails.name = name.value.toString()
        customerDetails.email = email.value.toString()
        customerDetails.password = password.value.toString()
        customerDetails.phone = phone.value.toString()
        customerDetails.address = address.value.toString()
        customerDetails.city = city.value.toString()
        customerDetails.pincode = pincode.value.toString()
    }

    private fun onError(message: String) {
        errorMessage.value = message
    }

    fun fetchCityUsingGPSData() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
            return
        else {
            val location: Location =
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)!!
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses: List<Address> = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            city.value = addresses[0].locality
            address.value = addresses[0].subLocality
            pincode.value = addresses[0].postalCode
        }

    }
}