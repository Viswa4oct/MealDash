package com.bitswilpg2.mealdash.network.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bitswilpg2.mealdash.databinding.AdapterMealPlanItemsBinding
import com.bitswilpg2.mealdash.network.models.MealPlanItem
import java.util.*

/**
 * @Author Viswa Teja
 * @Date 9/30/2021 6:06 PM
 */
class MealPlanItemsAdapter : RecyclerView.Adapter<MealPlanItemsViewHolder>() {

    private var mealPlanItemsList = mutableListOf<MealPlanItem>()
    private lateinit var activity: Activity

    fun setMealPlanItems(items: List<MealPlanItem>, activity: Activity) {
        this.mealPlanItemsList.clear()
        this.mealPlanItemsList = items.toMutableList()
        this.activity = activity
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealPlanItemsViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterMealPlanItemsBinding.inflate(inflater, parent, false)
        return MealPlanItemsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealPlanItemsViewHolder, position: Int) {
        val item = mealPlanItemsList[position]
        holder.bindItem(item)
    }

    override fun getItemCount(): Int {
        return mealPlanItemsList.size
    }
}

class MealPlanItemsViewHolder(private val adapterMealPlanItemsBinding: AdapterMealPlanItemsBinding
) : RecyclerView.ViewHolder(adapterMealPlanItemsBinding.root) {

    fun bindItem(mealPlanItem: MealPlanItem) {
        adapterMealPlanItemsBinding.mealPlanItemName.text = mealPlanItem.description
        adapterMealPlanItemsBinding.price.text = mealPlanItem.finalPrice.toString()
        adapterMealPlanItemsBinding.mealPlanType.text = "Type: " + mealPlanItem.type.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
        adapterMealPlanItemsBinding.mealPlanStatus.text = "Status: " + mealPlanItem.status.lowercase()
            .replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }
    }
}