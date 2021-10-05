package com.bitswilpg2.mealdash.screens

import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.regex.Matcher
import java.util.regex.Pattern


/**
 * @Author Viswa Teja
 * @Date 9/15/2021 8:24 PM
 */
class RegisterFragment: Fragment() {

    lateinit var binding: FragmentRegisterBinding
    private lateinit var navController: NavController
    private lateinit var locationManager: LocationManager
    private lateinit var registerViewModel: RegisterViewModel
    private val registerService = AuthenticationRetrofitService.getInstance()
    private val registerRepository = RegisterRepository(registerService)
    private lateinit var customerDetails: CustomerDetails
    private lateinit var alertDialogBuilder: MaterialAlertDialogBuilder

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_register, container, false)
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        if (activity != null) {
            locationManager =
                requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }

        customerDetails = CustomerDetails("","","","","","","")
        registerViewModel = ViewModelProvider(this, RegisterViewModelFactory(registerRepository, customerDetails, requireContext(), locationManager, navController)).get(RegisterViewModel::class.java)
        binding.registerViewModel = registerViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alertDialogBuilder = MaterialAlertDialogBuilder(requireContext())

        binding.btnRegister.setOnClickListener {
            navController.navigate(R.id.action_registerFragment_to_loginFragment)
        }
        binding.btnClose.setOnClickListener {
            navController.navigate(R.id.action_registerFragment_to_loginFragment)
        }

        registerViewModel.fetchCityUsingGPSData()

        registerViewModel.loading.observe(viewLifecycleOwner, {
            if (it) {
                binding.progressDialog.visibility = View.VISIBLE
            } else {
                binding.progressDialog.visibility = View.GONE
            }
        })

        registerViewModel.errorMessage.observe(viewLifecycleOwner, {
            alertDialogBuilder.setMessage(it).show()
        })
    }
}