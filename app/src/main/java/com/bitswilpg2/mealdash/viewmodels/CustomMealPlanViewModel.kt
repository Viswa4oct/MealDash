package com.bitswilpg2.mealdash.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.bitswilpg2.mealdash.R
import com.bitswilpg2.mealdash.network.models.Item
import com.bitswilpg2.mealdash.network.repository.FoodItemsRepository
import kotlinx.coroutines.*

/**
 * @Author Viswa Teja
 * @Date 10/1/2021 2:47 PM
 */
class CustomMealPlanViewModel(
    private val foodItemsRepository: FoodItemsRepository,
    private val navController: NavController
): ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val items = MutableLiveData<Item>()
    var job: Job? = null
    val loading = MutableLiveData<Boolean>()

    fun getItemList() {
        loading.value = true

        job = CoroutineScope(Dispatchers.IO).launch {
            val response = foodItemsRepository.getItemList()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    items.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    fun goToCart() {
        navController.navigate(R.id.action_customMealPlanFragment_to_cartFragment)
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