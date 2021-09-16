package com.bitswilpg2.mealdash.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bitswilpg2.mealdash.R
import com.bitswilpg2.mealdash.databinding.FragmentSearchBinding

/**
 * @Author Viswa Teja
 * @Date 9/6/2021 8:48 PM
 */
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_search, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bestRestaurants =
            listOf("Restaurant A", "Restaurant B", "Restaurant C", "Restaurant D", "Restaurant E", "Restaurant F")
        var adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, bestRestaurants)
        binding.searchRestaurants.adapter = adapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                //Performs search when user hit the search button on the keyboard
                if (bestRestaurants.contains(p0)) {
                    adapter.filter.filter(p0)
                }
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                //Start filtering the list as user start entering the characters
                adapter.filter.filter(p0)
                return false
            }
        })
    }
}