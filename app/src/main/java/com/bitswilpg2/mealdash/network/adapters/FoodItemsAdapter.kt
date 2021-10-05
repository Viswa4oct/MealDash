package com.bitswilpg2.mealdash.network.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bitswilpg2.mealdash.databinding.AdapterFoodItemsBinding
import com.bitswilpg2.mealdash.network.models.ItemDetails
import com.bitswilpg2.mealdash.screens.CheckBoxInterface

/**
 * @Author Viswa Teja
 * @Date 10/1/2021 12:35 PM
 */
class FoodItemsAdapter(private var checkBoxInterface: CheckBoxInterface?) : RecyclerView.Adapter<FoodItemsViewHolder>() {

    private var foodItemsList = mutableListOf<ItemDetails>()
    private lateinit var activity: Activity

    fun setFoodItems(items: List<ItemDetails>, activity: Activity) {
        this.foodItemsList.clear()
        this.foodItemsList = items.toMutableList()
        this.activity = activity
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodItemsViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterFoodItemsBinding.inflate(inflater, parent, false)
        return FoodItemsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodItemsViewHolder, position: Int) {
        val item = foodItemsList[position]
        holder.bindItem(item, checkBoxInterface)
    }

    override fun getItemCount(): Int {
        return foodItemsList.size
    }
}

class FoodItemsViewHolder(private val adapterFoodItemsBinding: AdapterFoodItemsBinding
) : RecyclerView.ViewHolder(adapterFoodItemsBinding.root) {

    fun bindItem(itemDetails: ItemDetails, checkBoxInterface: CheckBoxInterface?) {
        adapterFoodItemsBinding.mealPlanItemName.text = itemDetails.description
        adapterFoodItemsBinding.price.text = itemDetails.price.toString()
        adapterFoodItemsBinding.addToCart.setOnCheckedChangeListener { _, _ ->
            checkBoxInterface!!.getCheckedItemDetails(itemDetails)
        }
    }
}