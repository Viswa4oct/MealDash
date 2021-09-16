package com.bitswilpg2.mealdash.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bitswilpg2.mealdash.R
import com.bitswilpg2.mealdash.databinding.FragmentCartBinding

/**
 * @Author Viswa Teja
 * @Date 9/6/2021 8:50 PM
 */
class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_cart, container, false)
        return binding.root
    }
}