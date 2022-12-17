package com.example.pathfinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.pathfinder.databinding.ActivityMainBinding
import com.example.pathfinder.login.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)

        drawerLayout = binding.drawerLayout
        val navController = findNavController(R.id.myNavHostFragment)

        val navigationView: NavigationView = binding.navView

        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    navController.navigate(R.id.homeFragment)
                }
                R.id.createPathFragment -> {
                    navController.navigate(R.id.createPathFragment)
                }
                R.id.viewPathFragment -> {
                    navController.navigate(R.id.viewPathFragment)
                }
                R.id.aboutFragment -> {
                    navController.navigate(R.id.aboutFragment)
                }
                R.id.settingsFragment -> {
                    navController.navigate(R.id.settingsFragment)
                }
            }
            true
        }

        // Get a reference to the Toolbar in the layout
        val toolbar = findViewById<Toolbar>(R.id.app_bar)

        // Attach the Toolbar to the activity
        setSupportActionBar(toolbar)

        bottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> navController.navigate(R.id.homeFragment)
                R.id.menu_add_path -> navController.navigate(R.id.createPathFragment)
                R.id.menu_view_path -> navController.navigate(R.id.viewPathFragment)
                R.id.menu_about_us -> navController.navigate(R.id.aboutFragment)
                R.id.menu_settings -> navController.navigate(R.id.settingsFragment)
            }
            true
        }

        NavigationUI.setupActionBarWithNavController(this,navController,drawerLayout)
        NavigationUI.setupWithNavController(binding.navView,navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController,drawerLayout)
    }


}