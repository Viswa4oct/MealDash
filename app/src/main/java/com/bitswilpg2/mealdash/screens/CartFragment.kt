package com.bitswilpg2.mealdash.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bitswilpg2.mealdash.R
import com.bitswilpg2.mealdash.databinding.FragmentCartBinding
import com.bitswilpg2.mealdash.databinding.FragmentLoginBinding
import com.bitswilpg2.mealdash.network.models.Cart
import com.bitswilpg2.mealdash.network.models.CartItem
import com.bitswilpg2.mealdash.network.models.ItemDetails
import com.bitswilpg2.mealdash.network.models.LoginDetails
import com.bitswilpg2.mealdash.network.repository.CartRepository
import com.bitswilpg2.mealdash.network.repository.LoginRepository
import com.bitswilpg2.mealdash.network.services.AuthenticationRetrofitService
import com.bitswilpg2.mealdash.network.services.CoreRetrofitService
import com.bitswilpg2.mealdash.viewmodels.CartViewModel
import com.bitswilpg2.mealdash.viewmodels.LoginViewModel
import com.bitswilpg2.mealdash.viewmodels.factory.CartViewModelFactory
import com.bitswilpg2.mealdash.viewmodels.factory.LoginViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText

/**
 * @Author Viswa Teja
 * @Date 9/6/2021 8:50 PM
 */
class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    var bundle = Bundle()
    private lateinit var finalPrice: TextInputEditText
    private lateinit var quantity: TextInputEditText
    private lateinit var cartViewModel: CartViewModel
    private val cartService = CoreRetrofitService.getResInstance()
    private val cartRepository = CartRepository(cartService)
    private lateinit var cart: Cart
    private lateinit var cartItem:CartItem
    private lateinit var itemDetails: ItemDetails
    private lateinit var alertDialogBuilder: MaterialAlertDialogBuilder

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_cart, container, false)

        if(requireArguments()!=null) {
            bundle = requireArguments()

            itemDetails = bundle.getParcelable("itemDetails")!!

            cartItem = CartItem(itemDetails.id, 0)
            cart = Cart(
                itemDetails.price,
                itemDetails.description,
                0.0,
                listOf(cartItem),
                itemDetails.status,
                "custom"
            )
            cartViewModel = ViewModelProvider(
                this,
                CartViewModelFactory(cartRepository, cart, cartItem, bundle.getInt("ResID"))
            ).get(
                CartViewModel::class.java
            )
            binding.cartViewModel = cartViewModel

        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        finalPrice = binding.edtFinalPrice
        quantity = binding.edtQuantity
        alertDialogBuilder = MaterialAlertDialogBuilder(requireContext())

        cartViewModel.errorMessage.observe(viewLifecycleOwner, {
            alertDialogBuilder.setMessage(it).show()
        })
    }

}