package com.bitswilpg2.mealdash.screens

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
import androidx.recyclerview.widget.LinearSnapHelper
import com.bitswilpg2.mealdash.R
import com.bitswilpg2.mealdash.databinding.FragmentRestaurantDetailBinding
import com.bitswilpg2.mealdash.network.repository.MealPlanRepository
import com.bitswilpg2.mealdash.network.services.CoreRetrofitService
import com.bitswilpg2.mealdash.viewmodels.RestaurantDetailViewModel
import com.bitswilpg2.mealdash.viewmodels.factory.RestaurantDetailViewModelFactory
import androidx.recyclerview.widget.RecyclerView
import com.bitswilpg2.mealdash.network.adapters.MealPlanItemsAdapter


/**
 * @Author Viswa Teja
 * @Date 9/30/2021 4:22 PM
 */
class RestaurantDetailFragment : Fragment() {

    private lateinit var binding: FragmentRestaurantDetailBinding
    private lateinit var navController: NavController
    private val mealPlanItemsAdapter = MealPlanItemsAdapter()
    private lateinit var restaurantDetailViewModel: RestaurantDetailViewModel
    private val coreService = CoreRetrofitService.getInstance()
    private val mealPlanRepository = MealPlanRepository(coreService)
    private lateinit var linearSnapHelper: LinearSnapHelper
    private var bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_restaurant_detail, container, false)
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        bundle = requireArguments()

        linearSnapHelper = LinearSnapHelper()
        linearSnapHelper.attachToRecyclerView(binding.itemsRecyclerView)
        newScrollTo(0)
        binding.itemsRecyclerView.adapter = mealPlanItemsAdapter
        restaurantDetailViewModel = ViewModelProvider(this, RestaurantDetailViewModelFactory(mealPlanRepository, navController)).get(
            RestaurantDetailViewModel::class.java)
        binding.mealPlanItemsViewModel = restaurantDetailViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*binding.btnProfile.setOnClickListener {
            navController.navigate(R.id.action_restaurantsFragment_to_profileFragment)
        }*/

        restaurantDetailViewModel.mealPlan.observe(viewLifecycleOwner, {
            mealPlanItemsAdapter.setMealPlanItems(it, requireActivity())
        })

        restaurantDetailViewModel.loading.observe(viewLifecycleOwner, {
            if (it) {
                binding.progressDialog.visibility = View.VISIBLE
            } else {
                binding.progressDialog.visibility = View.GONE
            }
        })

        restaurantDetailViewModel.errorMessage.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        restaurantDetailViewModel.getMealPlanList(bundle.getInt("ResID"))
    }

    fun newScrollTo(pos: Int) {
        binding.itemsRecyclerView.smoothScrollToPosition(pos)
        val vh: RecyclerView.ViewHolder? = binding.itemsRecyclerView.findViewHolderForLayoutPosition(pos)
        if (vh != null) {
            // Target view is available, so just scroll to it.
            binding.itemsRecyclerView.smoothScrollToPosition(pos)
        } else {
            // Target view is not available. Scroll to it.
            binding.itemsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                // From the documentation:
                // This callback will also be called if visible item range changes after a layout
                // calculation. In that case, dx and dy will be 0.This callback will also be called
                // if visible item range changes after a layout calculation. In that case,
                // dx and dy will be 0.
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    binding.itemsRecyclerView.removeOnScrollListener(this)
                    if (dx == 0) {
                        newScrollTo(pos)
                    }
                }
            })
            binding.itemsRecyclerView.scrollToPosition(pos)
        }
    }
}