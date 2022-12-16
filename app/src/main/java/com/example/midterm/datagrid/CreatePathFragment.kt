package com.example.midterm.datagrid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.midterm.R
import com.example.midterm.database.PathDatabase
import com.example.midterm.databinding.FragmentCreatePathBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class CreatePathFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentCreatePathBinding>(
            inflater,
            R.layout.fragment_create_path,
            container,
            false
        )
        binding.btnaddexit.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_createPathFragment_to_homeFragment) }

        val application = requireNotNull(this.activity).application

        val dataSource = PathDatabase.getInstance(application).pathDatabaseDao

        val viewModelFactory = CreatePathViewModelFactory(dataSource, application)

        val createPathViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(CreatePathViewModel::class.java)

        createPathViewModel._error.observe(viewLifecycleOwner, Observer { message ->
            Toast.makeText(activity?.applicationContext, message, Toast.LENGTH_SHORT).show()
        })

        createPathViewModel._success.observe(viewLifecycleOwner, Observer { message ->
            Toast.makeText(activity?.applicationContext, message, Toast.LENGTH_SHORT).show()
        })

        binding.createPathViewModel = createPathViewModel

        binding.setLifecycleOwner(this)



        return binding.root
    }

    override fun onResume() {
        super.onResume()

        // Hide the bottom navigation view
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView?.visibility = View.VISIBLE

//        val toolbar = activity?.findViewById<Toolbar>(R.id.app_bar)
//        toolbar?.visibility = View.VISIBLE
    }

}