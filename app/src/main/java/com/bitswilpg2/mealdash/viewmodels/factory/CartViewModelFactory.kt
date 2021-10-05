package com.bitswilpg2.mealdash.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bitswilpg2.mealdash.network.models.Cart
import com.bitswilpg2.mealdash.network.models.CartItem
import com.bitswilpg2.mealdash.network.repository.CartRepository
import com.bitswilpg2.mealdash.viewmodels.CartViewModel

/**
 * @Author Viswa Teja
 * @Date 10/1/2021 6:57 PM
 */
class CartViewModelFactory(
    private val cartRepository: CartRepository,
    private val cart: Cart,
    private val cartItem: CartItem,
    private val resID: Int
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            CartViewModel(this.cartRepository, this.cart, this.cartItem, this.resID) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}