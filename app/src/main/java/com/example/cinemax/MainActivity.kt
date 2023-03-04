package com.example.cinemax


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cinemax.databinding.ActivityMainBinding

import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import com.example.cinemax.utils.gone
import com.example.cinemax.utils.show


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var doubleBackToExitPressedOnce = false
    private lateinit var bottomNavigationView : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        supportActionBar?.hide()

        bottomNavigationView = binding.bottomNavigationViewMain
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        bottomNavigationView.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.searchFragment,
                R.id.wishlistFragment,
                R.id.profileFragment
            )
        )
        setupActionBarWithNavController(navController,appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.entryFragment -> bottomNavigationView.gone()
                R.id.verificationFragment-> bottomNavigationView.gone()
                R.id.loginFragment ->bottomNavigationView.gone()
                R.id.newPasswordFragment -> bottomNavigationView.gone()
                R.id.resetPasswordFragment -> bottomNavigationView.gone()
                R.id.signUpFragment -> bottomNavigationView.gone()
                R.id.splashFragment -> bottomNavigationView.gone()
                R.id.viewPagerFragment -> bottomNavigationView.gone()
                R.id.searchResultFragment -> bottomNavigationView.gone()
                else -> bottomNavigationView.show()
            }
        }

    }

    override fun onBackPressed() {
        if (bottomNavigationView.isVisible){
            if (doubleBackToExitPressedOnce) {
                finish()
            }
            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Please press back again to exit", Toast.LENGTH_SHORT).show()

            Handler(Looper.myLooper()!!).postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
        }
    }
}