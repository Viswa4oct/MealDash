package com.bitswilpg2.mealdash.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bitswilpg2.mealdash.R
import com.bitswilpg2.mealdash.databinding.FragmentRegisterBinding
import android.location.Geocoder
import java.util.*


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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchCityUsingGPSData()

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        val l = { _: View -> } // empty listener so that touch effects are visible
        binding.btnRegister.setOnClickListener {
            navController.navigate(R.id.action_registerFragment_to_loginFragment)
        }
        binding.btnClose.setOnClickListener {
            navController.navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun fetchCityUsingGPSData() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
                return
        else {
            val location: Location =
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)!!
            longitude = location.longitude
            latitude = location.latitude
        }
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1)
        val cityName = addresses[0].locality

        binding.edtLocation.setText(cityName)
    }
}