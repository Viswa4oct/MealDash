package com.bitswilpg2.mealdash.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bitswilpg2.mealdash.R
import com.bitswilpg2.mealdash.databinding.FragmentLoginBinding
import com.bitswilpg2.mealdash.databinding.FragmentProfileBinding

/**
 * @Author Viswa Teja
 * @Date 9/6/2021 8:51 PM
 */
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        val l = { _: View -> } // empty listener so that touch effects are visible
        binding.btnClose.setOnClickListener {
            navController.navigate(R.id.action_profileFragment_to_restaurantsFragment)
        }
    }
}