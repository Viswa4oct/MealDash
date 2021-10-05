package com.bitswilpg2.mealdash.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.bitswilpg2.mealdash.network.repository.FoodItemsRepository
import com.bitswilpg2.mealdash.viewmodels.CustomMealPlanViewModel

/**
 * @Author Viswa Teja
 * @Date 10/1/2021 2:50 PM
 */
class CustomMealPlanViewModelFactory (
    private val foodItemsRepository: FoodItemsRepository,
    private val navController: NavController
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CustomMealPlanViewModel::class.java)) {
            CustomMealPlanViewModel(this.foodItemsRepository, this.navController) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}