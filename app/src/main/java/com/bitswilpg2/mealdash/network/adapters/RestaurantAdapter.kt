package com.bitswilpg2.mealdash.network.adapters

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.bitswilpg2.mealdash.R
import com.bitswilpg2.mealdash.databinding.AdapterRestaurantBinding
import com.bitswilpg2.mealdash.network.models.RestaurantItem
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

/**
 * @Author Viswa Teja
 * @Date 9/29/2021 4:30 PM
 */
class RestaurantAdapter() : RecyclerView.Adapter<RestaurantViewHolder>() {

    var restaurantList = mutableListOf<RestaurantItem>()
    private var latitude:Double = 0.0
    private var longitude:Double = 0.0
    private lateinit var activity: Activity
    private lateinit var fragment: Fragment

    fun setRestaurants(restaurants: List<RestaurantItem>, latitude: Double, longitude: Double, activity: Activity, parentFragment: Fragment) {
        this.restaurantList.clear()
        this.restaurantList = restaurants.toMutableList()
        this.latitude = latitude
        this.longitude = longitude
        this.activity = activity
        this.fragment = parentFragment
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterRestaurantBinding.inflate(inflater, parent, false)
        return RestaurantViewHolder(binding, object : RecyclerItemClickListener {
            override fun onItemClick(pos: Int) {

                val currentFragment =
                    NavHostFragment.findNavController(fragment).currentDestination?.id
                if (currentFragment == R.id.restaurantsFragment) {
                    val bundle = Bundle()
                    bundle.putInt("ResID", restaurantList[pos].id)
                    Navigation.findNavController(activity, R.id.nav_host_fragment)
                        .navigate(R.id.action_restaurantsFragment_to_restaurantDetailFragment, bundle)
                } else if(currentFragment == R.id.searchFragment) {
                    Navigation.findNavController(activity, R.id.nav_host_fragment)
                        .navigate(R.id.action_searchFragment_to_restaurantDetailFragment)
                }
            }
        })
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = restaurantList[position]
        holder.bindRestaurant(restaurant, latitude, longitude)
        holder.itemView.setOnClickListener { holder.recyclerItemClickListener.onItemClick(position) }
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }
}

interface RecyclerItemClickListener {
    fun onItemClick(pos: Int)
}

class RestaurantViewHolder(private val adapterRestaurantBinding: AdapterRestaurantBinding,
                           var recyclerItemClickListener: RecyclerItemClickListener
) : RecyclerView.ViewHolder(adapterRestaurantBinding.root) {

    fun bindRestaurant(restaurantItem: RestaurantItem, latitude: Double, longitude: Double) {
        adapterRestaurantBinding.name.text = restaurantItem.name
        adapterRestaurantBinding.distance.text = "Distance: " + distance(latitude,longitude, restaurantItem.latitude.toDouble(), restaurantItem.longitude.toDouble()).toString() + " KM"
    }

    private fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Int {
        val theta = lon1 - lon2
        var dist = (sin(deg2rad(lat1))
                * sin(deg2rad(lat2))
                + (cos(deg2rad(lat1))
                * cos(deg2rad(lat2))
                * cos(deg2rad(theta))))
        dist = acos(dist)
        dist = rad2deg(dist)
        dist *= 60
        return dist.toInt()
    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }
}
