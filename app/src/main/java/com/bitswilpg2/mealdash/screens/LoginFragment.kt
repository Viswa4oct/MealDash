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
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bitswilpg2.mealdash.R
import com.bitswilpg2.mealdash.databinding.FragmentLoginBinding
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

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        username = binding.edtUserName
        password = binding.edtPassword

        val l = { _: View -> } // empty listener so that touch effects are visible
        binding.btnLogin.setOnClickListener {
            if (username.text!!.toString() == "viswa" && password.text!!.toString() == "0000") {
                navController.navigate(R.id.action_loginFragment_to_restaurantsFragment)
            } else {
                Toast.makeText(activity, "Login Failed!", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnSignUp.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onStart() {
        super.onStart()

        if(ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            checkPermissions()
        }
    }
}