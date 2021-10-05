package com.bitswilpg2.mealdash.screens

import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bitswilpg2.mealdash.R
import com.bitswilpg2.mealdash.databinding.FragmentRestaurantsBinding
import com.bitswilpg2.mealdash.network.adapters.RestaurantAdapter
import com.bitswilpg2.mealdash.network.repository.RestaurantRepository
import com.bitswilpg2.mealdash.network.services.CoreRetrofitService
import com.bitswilpg2.mealdash.viewmodels.RestaurantViewModel
import com.bitswilpg2.mealdash.viewmodels.factory.RestaurantViewModelFactory

/**
 * @Author Viswa Teja
 * @Date 9/6/2021 8:36 PM
 */
class RestaurantsFragment : Fragment() {

    private lateinit var binding: FragmentRestaurantsBinding
    private lateinit var navController: NavController
    private lateinit var locationManager: LocationManager
    private val restaurantAdapter = RestaurantAdapter()
    private lateinit var restaurantViewModel: RestaurantViewModel
    private val coreService = CoreRetrofitService.getInstance()
    private val restaurantRepository = RestaurantRepository(coreService)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_restaurants, container, false)

        if (activity != null) {
            locationManager =
                requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }

        binding.restaurantRecyclerView.adapter = restaurantAdapter
        restaurantViewModel = ViewModelProvider(this, RestaurantViewModelFactory(restaurantRepository, locationManager)).get(RestaurantViewModel::class.java)
        binding.restaurantViewModel = restaurantViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        binding.btnProfile.setOnClickListener {
            navController.navigate(R.id.action_restaurantsFragment_to_profileFragment)
        }

        restaurantViewModel.restaurant.observe(viewLifecycleOwner, {
            restaurantAdapter.setRestaurants(it, restaurantViewModel.latitude, restaurantViewModel.longitude, requireActivity(), requireParentFragment())
        })

        restaurantViewModel.loading.observe(viewLifecycleOwner, {
            if (it) {
                binding.progressDialog.visibility = View.VISIBLE
            } else {
                binding.progressDialog.visibility = View.GONE
            }
        })

        restaurantViewModel.errorMessage.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        restaurantViewModel.getRestaurantList()
    }
}