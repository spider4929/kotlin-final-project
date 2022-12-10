package com.example.midterm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.midterm.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
//    private lateinit var drawerLayout: DrawerLayout
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)

        val navController = findNavController(R.id.myNavHostFragment)
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

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = this.findNavController(R.id.myNavHostFragment)
//        return NavigationUI.navigateUp(navController,drawerLayout)
//    }

    }
    fun showBottomNavigation() {
        bottomNavigationView.visibility = VISIBLE
    }

    fun hideBottomNavigation() {
        bottomNavigationView.visibility = GONE
    }
}