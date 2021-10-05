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
import androidx.navigation.NavController
import com.bitswilpg2.mealdash.R
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
    private var locationManager: LocationManager,
    private var navController: NavController
)  : ViewModel() {

    var name = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var phone = MutableLiveData<String>()
    var address = MutableLiveData<String>()
    var city = MutableLiveData<String>()
    var pincode = MutableLiveData<String>()
    val errorMessage = MutableLiveData<String>()
    var job: Job? = null
    val loading = MutableLiveData<Boolean>()

    fun registerCustomers() {
        loading.value = true
        setCustomerDetails()

        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.registerCustomers(customerDetails)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    navController.navigate(R.id.action_registerFragment_to_loginFragment)
                    errorMessage.value = "Registered Successfully. Please Login to continue."
                    loading.value = false
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
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    fun fetchCityUsingGPSData() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
            return
        else {
            val location: Location =
                locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)!!
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses: List<Address> = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            city.value = addresses[0].locality
            address.value = addresses[0].subLocality
            pincode.value = addresses[0].postalCode
        }

    }
}