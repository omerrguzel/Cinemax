package com.example.cinemax


import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cinemax.databinding.ActivityMainBinding
import com.example.cinemax.utils.LanguageConfig
import com.example.cinemax.utils.SharedPrefManager
import com.example.cinemax.utils.gone
import com.example.cinemax.utils.show
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var sharedPrefManager: SharedPrefManager
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var doubleBackToExitPressedOnce = false
    private lateinit var bottomNavigationView : BottomNavigationView

    private val bottomNavFragments = setOf(
        R.id.homeFragment,
        R.id.searchFragment,
        R.id.wishlistFragment,
        R.id.profileFragment
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }


    override fun attachBaseContext(newBase: Context) {
        sharedPrefManager = SharedPrefManager(newBase)
        val languageCode: String = sharedPrefManager.getLocale()
        val context: Context = LanguageConfig.changeLanguage(newBase, languageCode)
        super.attachBaseContext(context)
    }

    private fun init() {
        supportActionBar?.hide()

        bottomNavigationView = binding.bottomNavigationViewMain
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        bottomNavigationView.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(bottomNavFragments)

        setupActionBarWithNavController(navController,appBarConfiguration)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (bottomNavFragments.contains(destination.id)) {
                bottomNavigationView.show()
            } else {
                bottomNavigationView.gone()
            }
        }
    }


    override fun onBackPressed() {
        if (bottomNavigationView.isVisible){
            if (doubleBackToExitPressedOnce) {
                finish()
            }
            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, getString(R.string.press_back_again), Toast.LENGTH_SHORT).show()

            Handler(Looper.myLooper()!!).postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
        } else {
            val fragmentManager: FragmentManager = supportFragmentManager
            val backStackEntryCount: Int = fragmentManager.backStackEntryCount
            if (backStackEntryCount > 0) {
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            } else {
                super.getOnBackPressedDispatcher().onBackPressed()
            }
        }
    }
}