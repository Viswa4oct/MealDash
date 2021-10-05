package com.bitswilpg2.mealdash.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bitswilpg2.mealdash.network.models.Cart
import com.bitswilpg2.mealdash.network.models.CartItem
import com.bitswilpg2.mealdash.network.repository.CartRepository
import kotlinx.coroutines.*

/**
 * @Author Viswa Teja
 * @Date 10/1/2021 6:25 PM
 */
class CartViewModel(
    private val repository: CartRepository,
    private var cart: Cart,
    private var cartItem: CartItem,
    private var resID: Int
)  : ViewModel() {

    var description = MutableLiveData<String>()
    var actualPrice = MutableLiveData<String>()
    var finalPrice = MutableLiveData<String>()
    var type = MutableLiveData<String>()
    var status = MutableLiveData<String>()
    var quantity = MutableLiveData<String>()
    var itemId = MutableLiveData<String>()
    val errorMessage = MutableLiveData<String>()
    var job: Job? = null
    val loading = MutableLiveData<Boolean>()

    fun postCartDetails() {
        loading.value = true
        setCartDetails()

        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.postCartDetails(cart, resID)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    errorMessage.value = "Custom MealPlan Request for " + cart.description + " Created Successfully"
                    loading.value = false
                } else {
                    onError("Cart Failed : ${response.message()} ")
                }
            }
        }
    }

    private fun setCartDetails() {
        cart.actualPrice = 20.0
        cart.finalPrice = 15.0
        cart.description = "Item"
        cart.status = "Available"
        cart.type = "custom"
        cartItem.itemId = 1
        cartItem.quantity = 3
        cart.cartItems = listOf(cartItem)
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