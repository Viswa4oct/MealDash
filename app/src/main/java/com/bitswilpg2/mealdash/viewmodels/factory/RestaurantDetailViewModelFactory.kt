package com.bitswilpg2.mealdash.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.bitswilpg2.mealdash.network.repository.MealPlanRepository
import com.bitswilpg2.mealdash.viewmodels.RestaurantDetailViewModel

/**
 * @Author Viswa Teja
 * @Date 9/30/2021 6:04 PM
 */
class RestaurantDetailViewModelFactory (
    private val mealPlanRepository: MealPlanRepository,
    private val navController: NavController
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(RestaurantDetailViewModel::class.java)) {
            RestaurantDetailViewModel(this.mealPlanRepository, this.navController) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}