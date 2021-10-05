package com.bitswilpg2.mealdash.viewmodels

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.bitswilpg2.mealdash.R
import com.bitswilpg2.mealdash.network.models.MealPlan
import com.bitswilpg2.mealdash.network.repository.MealPlanRepository
import kotlinx.coroutines.*

/**
 * @Author Viswa Teja
 * @Date 9/30/2021 5:54 PM
 */
class RestaurantDetailViewModel(
    private val mealPlanRepository: MealPlanRepository,
    private var navController: NavController
): ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val mealPlan = MutableLiveData<MealPlan>()
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    var job: Job? = null
    val loading = MutableLiveData<Boolean>()
    private var resID: Int = 0

    fun getMealPlanList(resID: Int) {
        this.resID = resID
        loading.value = true

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = mealPlanRepository.getMealPlanList()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    mealPlan.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    fun goToCustomMealPlan() {
        val bundle = Bundle()
        bundle.putInt("ResID", resID)
        navController.navigate(R.id.action_restaurantDetailFragment_to_customMealPlanFragment, bundle)
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