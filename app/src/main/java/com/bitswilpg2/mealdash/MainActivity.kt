package com.bitswilpg2.mealdash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.bitswilpg2.mealdash.databinding.ActivityMainBinding
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.CustomActionBarTheme)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupNavigation()
    }

    private fun setupNavigation() {
        val navHostFragment: NavHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val navInflater = navController.navInflater
        val navGraph = navInflater.inflate(R.navigation.nav_graph)
        val badgeDrawable : BadgeDrawable = binding.bottomNavigationView.getOrCreateBadge(R.id.cartFragment)
        badgeDrawable.number = 0
        navGraph.startDestination = R.id.loginFragment
        navController.graph = navGraph
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
        navController.addOnDestinationChangedListener(this)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        if (R.id.loginFragment == destination.id || R.id.registerFragment == destination.id || R.id.profileFragment == destination.id)
            binding.bottomNavigationView.visibility = View.GONE
        else {
            if (R.id.cartFragment == destination.id) {
                binding.bottomNavigationView.getBadge(destination.id)?.let { badgeDrawable ->
                    if(badgeDrawable.isVisible)
                        binding.bottomNavigationView.removeBadge(destination.id)
                }
            }
            binding.bottomNavigationView.visibility = View.VISIBLE
        }
    }
}