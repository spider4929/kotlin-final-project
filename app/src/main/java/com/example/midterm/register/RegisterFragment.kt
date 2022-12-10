package com.example.midterm.register

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.midterm.R
import com.example.midterm.database.UserDatabase
import com.example.midterm.databinding.FragmentRegisterBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class RegisterFragment : Fragment() {
//    private lateinit var bottomNavigationView: BottomNavigationView

    @SuppressLint("FragmentLiveDataObserve")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentRegisterBinding>(
            inflater,
            R.layout.fragment_register,
            container,
            false
        )

//        bottomNavigationView = requireView().findViewById(R.id.bottom_navigation)
//
//        bottomNavigationView.visibility = View.GONE

        binding.button.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_registerFragment_to_loginFragment2)
        }
            val application = requireNotNull(this.activity).application

            val dataSource = UserDatabase.getInstance(application).userDatabaseDao

            val viewModelFactory = RegisterViewModelFactory(dataSource, application)

            val registerViewModel =
                ViewModelProvider(
                    this, viewModelFactory
                ).get(RegisterViewModel::class.java)

            registerViewModel.navigateToLogin.observe(this, Observer {
                this.findNavController().navigate(
                    R.id.action_registerFragment_to_loginFragment
                )
                registerViewModel.doneNavigating()
            })

            binding.registerViewModel = registerViewModel

            binding.setLifecycleOwner(this)

            return binding.root
        }

    override fun onResume() {
        super.onResume()

        // Hide the bottom navigation view
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView?.visibility = View.GONE
    }

}