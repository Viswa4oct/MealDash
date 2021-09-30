package com.bitswilpg2.mealdash.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.bitswilpg2.mealdash.network.models.LoginDetails
import com.bitswilpg2.mealdash.network.repository.LoginRepository
import com.bitswilpg2.mealdash.viewmodels.LoginViewModel

/**
 * @Author Viswa Teja
 * @Date 9/29/2021 6:11 PM
 */
class LoginViewModelFactory(
    private val loginRepository: LoginRepository,
    private val loginDetails: LoginDetails,
    private var navController: NavController
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            LoginViewModel(this.loginRepository, this.loginDetails, this.navController) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}