package com.example.cinemax


import android.content.Context
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
import com.example.cinemax.utils.gone
import com.example.cinemax.utils.show
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var doubleBackToExitPressedOnce = false
    private lateinit var bottomNavigationView : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        detectLanguage()

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
                R.id.detailFragment -> bottomNavigationView.gone()
                R.id.countryFragment -> bottomNavigationView.gone()
                R.id.languageFragment -> bottomNavigationView.gone()
                R.id.editProfileFragment -> bottomNavigationView.gone()
                R.id.policyFragment -> bottomNavigationView.gone()
                R.id.videoFragment -> bottomNavigationView.gone()
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
        } else {
            val fragmentManager: FragmentManager = supportFragmentManager
            val backStackEntryCount: Int = fragmentManager.backStackEntryCount
            if (backStackEntryCount > 0) {
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            } else {
                super.onBackPressed()
            }
        }
    }

    private fun detectLanguage(){
        val sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        val language = sharedPreferences?.getString("language", null) // default value is "en"
        if(language == "tr"){
            // Current language is Turkish
            changeToTurkish()
        } else if ( language == "en"){
            // Current language is English
         changeToEnglish()
        }
    }

    private fun changeToTurkish(){
//        val locale = Locale("tr")
//        val resources = context?.resources
//        val configuration = resources?.configuration
//        configuration?.setLocale(locale)

        val locale = Locale("tr")
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        applicationContext.resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun changeToEnglish(){

        val locale = Locale("en")
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        applicationContext.resources.updateConfiguration(config, resources.displayMetrics)

//        val locale = Locale("en")
//        val resources = context?.resources
//        val configuration = resources?.configuration
//        Locale.setDefault(Locale.ENGLISH)

    }

}