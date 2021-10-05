package com.bitswilpg2.mealdash.viewmodels

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.text.TextUtils
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.bitswilpg2.mealdash.R
import com.bitswilpg2.mealdash.network.models.LoginDetails
import com.bitswilpg2.mealdash.network.repository.LoginRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import java.util.*

/**
 * @Author Viswa Teja
 * @Date 9/29/2021 5:59 PM
 */
class LoginViewModel(
    private val repository: LoginRepository,
    private var loginDetails: LoginDetails,
    private var navController: NavController
    )  : ViewModel() {

    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    val errorMessage = MutableLiveData<String>()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    var job: Job? = null
    val loading = MutableLiveData<Boolean>()

    fun loginCustomer() {
        loading.value = true
        setCustomerDetails()

        if ((loginDetails.email.isNotEmpty() && loginDetails.email != "null") &&
            (loginDetails.password.isNotEmpty() && loginDetails.password != "null")) {
            job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                val response = repository.loginCustomers(loginDetails)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        navController.navigate(R.id.action_loginFragment_to_restaurantsFragment)
                        errorMessage.value = "Welcome " + loginDetails.name
                        loading.value = false
                    } else {
                        onError("Login Failed : ${response.message()} ")
                    }
                }
            }
        } else {
            errorMessage.value = "Please enter valid credentials to continue."
            loading.value = false
        }
    }

    private fun setCustomerDetails() {
        loginDetails.name = "User"
        loginDetails.email = email.value.toString()
        loginDetails.password = password.value.toString()
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}