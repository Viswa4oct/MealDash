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
import com.bitswilpg2.mealdash.R
import com.bitswilpg2.mealdash.databinding.FragmentCustomMealPlanBinding
import com.bitswilpg2.mealdash.network.adapters.FoodItemsAdapter
import com.bitswilpg2.mealdash.network.models.ItemDetails
import com.bitswilpg2.mealdash.network.repository.FoodItemsRepository
import com.bitswilpg2.mealdash.network.services.CoreRetrofitService
import com.bitswilpg2.mealdash.viewmodels.CustomMealPlanViewModel
import com.bitswilpg2.mealdash.viewmodels.factory.CustomMealPlanViewModelFactory

/**
 * @Author Viswa Teja
 * @Date 10/1/2021 2:42 PM
 */
class CustomMealPlanFragment : Fragment(), CheckBoxInterface {

    private lateinit var binding: FragmentCustomMealPlanBinding
    private lateinit var navController: NavController
    private val foodItemsAdapter = FoodItemsAdapter(this)
    private lateinit var customMealPlanViewModel: CustomMealPlanViewModel
    private val coreService = CoreRetrofitService.getInstance()
    private val foodItemsRepository = FoodItemsRepository(coreService)
    var detailBundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_custom_meal_plan, container, false)
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        detailBundle = requireArguments()

        binding.itemsRecyclerView.adapter = foodItemsAdapter
        customMealPlanViewModel = ViewModelProvider(this, CustomMealPlanViewModelFactory(foodItemsRepository, navController)).get(
            CustomMealPlanViewModel::class.java)
        binding.customMealPlanViewModel = customMealPlanViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*binding.btnProfile.setOnClickListener {
            navController.navigate(R.id.action_restaurantsFragment_to_profileFragment)
        }*/

        customMealPlanViewModel.items.observe(viewLifecycleOwner, {
            foodItemsAdapter.setFoodItems(it, requireActivity())
        })

        customMealPlanViewModel.loading.observe(viewLifecycleOwner, {
            if (it) {
                binding.progressDialog.visibility = View.VISIBLE
            } else {
                binding.progressDialog.visibility = View.GONE
            }
        })

        customMealPlanViewModel.errorMessage.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        customMealPlanViewModel.getItemList()
    }

    override fun getCheckedItemDetails(itemDetails: ItemDetails) {
        val bundle = Bundle()
        bundle.putParcelable("itemDetails", itemDetails)
        bundle.putInt("ResID", detailBundle.getInt("ResID"))
        navController.navigate(R.id.action_customMealPlanFragment_to_cartFragment, bundle)
    }
}

interface CheckBoxInterface {
    fun getCheckedItemDetails(itemDetails: ItemDetails)
}