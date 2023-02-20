package com.example.testapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.testapp.databinding.ActivityMainBinding
import np.com.susanthapa.curved_bottom_navigation.CbnMenuItem
import np.com.susanthapa.curved_bottom_navigation.CurvedBottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: CurvedBottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController

        // need avd for the icons
        val menuItems = arrayOf(
            CbnMenuItem(
                R.drawable.ic_home,
                R.drawable.avd_home,
                R.id.homeFragment
            ),
            CbnMenuItem(
                R.drawable.ic_copy,
                R.drawable.avd_copy,
                R.id.leaderboardFragment
            ),
            CbnMenuItem(
                R.drawable.ic_bell,
                R.drawable.avd_bell,
                R.id.workoutFragment
            ),
            CbnMenuItem(
                R.drawable.ic_camera,
                R.drawable.avd_camera,
                R.id.cameraFragment
            ),
            CbnMenuItem(
                R.drawable.ic_user,
                R.drawable.avd_user,
                R.id.profileFragment
            )
        )

        binding.navBottom.setMenuItems(menuItems, 0)
        binding.navBottom.setupWithNavController(navController)
        bottomNavigationView = binding.navBottom
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}