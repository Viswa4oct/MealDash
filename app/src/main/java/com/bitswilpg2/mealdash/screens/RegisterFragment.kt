package com.bitswilpg2.mealdash.screens

import android.content.Context
import android.location.LocationManager
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
import com.bitswilpg2.mealdash.databinding.FragmentRegisterBinding
import com.bitswilpg2.mealdash.network.models.CustomerDetails
import com.bitswilpg2.mealdash.network.repository.RegisterRepository
import com.bitswilpg2.mealdash.network.services.AuthenticationRetrofitService
import com.bitswilpg2.mealdash.viewmodels.RegisterViewModel
import com.bitswilpg2.mealdash.viewmodels.factory.RegisterViewModelFactory


/**
 * @Author Viswa Teja
 * @Date 9/15/2021 8:24 PM
 */
class RegisterFragment: Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var navController: NavController
    private lateinit var locationManager: LocationManager
    private var longitude: Double = 0.0
    private var latitude: Double = 0.0
    private lateinit var registerViewModel: RegisterViewModel
    private val registerService = AuthenticationRetrofitService.getInstance()
    private val registerRepository = RegisterRepository(registerService)
    private lateinit var customerDetails: CustomerDetails

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_register, container, false)

        if (activity != null) {
            locationManager =
                requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }

        customerDetails = CustomerDetails("","","","","","","")
        registerViewModel = ViewModelProvider(this, RegisterViewModelFactory(registerRepository, customerDetails, requireContext(), locationManager)).get(RegisterViewModel::class.java)
        binding.registerViewModel = registerViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        binding.btnRegister.setOnClickListener {
            navController.navigate(R.id.action_registerFragment_to_loginFragment)
        }
        binding.btnClose.setOnClickListener {
            navController.navigate(R.id.action_registerFragment_to_loginFragment)
        }

        registerViewModel.fetchCityUsingGPSData()
    }
}