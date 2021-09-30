package com.bitswilpg2.mealdash.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bitswilpg2.mealdash.R
import com.bitswilpg2.mealdash.databinding.FragmentLoginBinding
import com.bitswilpg2.mealdash.network.models.CustomerDetails
import com.bitswilpg2.mealdash.network.models.LoginDetails
import com.bitswilpg2.mealdash.network.repository.LoginRepository
import com.bitswilpg2.mealdash.network.repository.RegisterRepository
import com.bitswilpg2.mealdash.network.services.AuthenticationRetrofitService
import com.bitswilpg2.mealdash.viewmodels.LoginViewModel
import com.bitswilpg2.mealdash.viewmodels.RegisterViewModel
import com.bitswilpg2.mealdash.viewmodels.factory.LoginViewModelFactory
import com.bitswilpg2.mealdash.viewmodels.factory.RegisterViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

/**
 * @Author Viswa Teja
 * @Date 9/15/2021 11:48 AM
 */
class LoginFragment : Fragment() {

    private lateinit var layout: View
    private lateinit var binding: FragmentLoginBinding
    private lateinit var navController: NavController
    private lateinit var username: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var loginViewModel: LoginViewModel
    private val loginService = AuthenticationRetrofitService.getInstance()
    private val loginRepository = LoginRepository(loginService)
    private lateinit var loginDetails: LoginDetails

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Log.i("Location Permission: ", "Granted")
            } else {
                Log.i("Location Permission: ", "Denied")
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_login, container, false)
        layout = binding.loginLayout

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        loginDetails = LoginDetails("","","")
        loginViewModel = ViewModelProvider(this, LoginViewModelFactory(loginRepository, loginDetails, navController)).get(LoginViewModel::class.java)
        binding.loginViewModel = loginViewModel

        return binding.root
    }

    private fun checkPermissions() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                layout.showSnackbar(
                    requireView(),
                    "Permission Granted!",
                    Snackbar.LENGTH_SHORT,
                    null
                ) {}
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                layout.showSnackbar(
                    requireView(),
                    "Location Permission is required",
                    Snackbar.LENGTH_SHORT,
                    "View"
                ) {
                    requestPermissionLauncher.launch(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                }
            }

            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }
        }
    }

    fun View.showSnackbar(
        view: View,
        msg: String,
        length: Int,
        actionMessage: CharSequence?,
        action: (View) -> Unit
    ) {
        val snackbar = Snackbar.make(view, msg, length)
        if (actionMessage != null) {
            snackbar.setAction(actionMessage) {
                action(this)
            }.show()
        } else {
            snackbar.show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = binding.edtUserName
        password = binding.edtPassword

        binding.btnSignUp.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }

        loginViewModel.loading.observe(viewLifecycleOwner, {
            if (it) {
                binding.progressDialog.visibility = View.VISIBLE
            } else {
                binding.progressDialog.visibility = View.GONE
            }
        })
    }

    override fun onStart() {
        super.onStart()

        if(ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            checkPermissions()
        }
    }
}