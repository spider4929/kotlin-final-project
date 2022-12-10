package com.example.midterm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.midterm.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }
    override fun onResume() {
        super.onResume()

        // Hide the bottom navigation view
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView?.visibility = View.VISIBLE
    }
}