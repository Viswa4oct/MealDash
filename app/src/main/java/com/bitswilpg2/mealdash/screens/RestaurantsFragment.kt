package com.bitswilpg2.mealdash.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bitswilpg2.mealdash.R
import com.bitswilpg2.mealdash.databinding.FragmentRestaurantsBinding

/**
 * @Author Viswa Teja
 * @Date 9/6/2021 8:36 PM
 */
class RestaurantsFragment : Fragment() {

    private lateinit var binding: FragmentRestaurantsBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_restaurants, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bestRestaurants =
            listOf("Restaurant A", "Restaurant B", "Restaurant C", "Restaurant D", "Restaurant E", "Restaurant F")
        var adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, bestRestaurants)
        binding.restaurantRecyclerView.adapter = adapter

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        val l = { _: View -> } // empty listener so that touch effects are visible
        binding.btnProfile.setOnClickListener {
            navController.navigate(R.id.action_restaurantsFragment_to_profileFragment)
        }
    }
}