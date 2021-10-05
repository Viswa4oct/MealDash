package com.bitswilpg2.mealdash.screens

import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bitswilpg2.mealdash.R
import com.bitswilpg2.mealdash.databinding.FragmentSearchBinding
import com.bitswilpg2.mealdash.network.adapters.RestaurantAdapter
import com.bitswilpg2.mealdash.network.models.RestaurantItem
import com.bitswilpg2.mealdash.network.repository.RestaurantRepository
import com.bitswilpg2.mealdash.network.services.CoreRetrofitService
import com.bitswilpg2.mealdash.viewmodels.RestaurantViewModel
import com.bitswilpg2.mealdash.viewmodels.factory.RestaurantViewModelFactory
import java.util.ArrayList

/**
 * @Author Viswa Teja
 * @Date 9/6/2021 8:48 PM
 */
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var navController: NavController
    private lateinit var locationManager: LocationManager
    private val restaurantAdapter = RestaurantAdapter()
    private lateinit var restaurantViewModel: RestaurantViewModel
    private val coreService = CoreRetrofitService.getInstance()
    private val restaurantRepository = RestaurantRepository(coreService)
    private val searchHistory: MutableList<RestaurantItem> = ArrayList<RestaurantItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_search, container, false)

        if (activity != null) {
            locationManager =
                requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }

        binding.searchRestaurants.adapter = restaurantAdapter
        restaurantViewModel = ViewModelProvider(this, RestaurantViewModelFactory(restaurantRepository, locationManager)).get(
            RestaurantViewModel::class.java)
        binding.restaurantViewModel = restaurantViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

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

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                //Performs search when user hit the search button on the keyboard
                searchHistory.clear()

                for (i in restaurantAdapter.restaurantList.indices) {
                    if (restaurantAdapter.restaurantList[i].name.contains(p0.toString())) {
                        searchHistory.add(restaurantAdapter.restaurantList[i])
                    }
                }

                if (p0 != "" && searchHistory.size == 0) {
                    restaurantAdapter.setRestaurants(searchHistory.toList(), restaurantViewModel.latitude, restaurantViewModel.longitude, requireActivity(), requireParentFragment())
                } else if (p0 != "" && searchHistory.size > 0) {
                    restaurantAdapter.setRestaurants(searchHistory.toList(), restaurantViewModel.latitude, restaurantViewModel.longitude, requireActivity(), requireParentFragment())
                } else {
                    restaurantAdapter.setRestaurants(restaurantAdapter.restaurantList, restaurantViewModel.latitude, restaurantViewModel.longitude, requireActivity(), requireParentFragment())
                }

                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                //Start filtering the list as user start entering the characters
                if(p0.equals("")) {
                    restaurantAdapter.setRestaurants(restaurantAdapter.restaurantList, restaurantViewModel.latitude, restaurantViewModel.longitude, requireActivity(), requireParentFragment())
                }
                return false
            }
        })
    }
}