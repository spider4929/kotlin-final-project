package com.example.pathfinder.datagrid

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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pathfinder.R
import com.example.pathfinder.database.PathDatabase
import com.example.pathfinder.databinding.FragmentViewPathBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

class ViewPathFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentViewPathBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_view_path, container, false
        )

        binding.btnviewexit.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_viewPathFragment_to_homeFragment) }

        val application = requireNotNull(this.activity).application

        val dataSource = PathDatabase.getInstance(application).pathDatabaseDao
        val viewModelFactory = ViewPathViewModelFactory(dataSource, application)

        val viewPathViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(ViewPathViewModel::class.java)

        binding.viewPathViewModel = viewPathViewModel

        val adapter = PathAdapter(PathListener { pathId ->
            viewPathViewModel.onPathClicked(pathId)
        })
        binding.pathList.adapter = adapter

        viewPathViewModel.paths.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        binding.setLifecycleOwner(this)

        viewPathViewModel.showSnackBarEvent.observe(viewLifecycleOwner, Observer {
            if (it == true){
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    "All paths has been deleted.",
                    Snackbar.LENGTH_SHORT
                ).show()
                viewPathViewModel.doneShowingSnackbar()
            }
        })

        viewPathViewModel.navigateToPathDetail.observe(viewLifecycleOwner, Observer { path ->
            path?.let {
                this.findNavController().navigate(
                    ViewPathFragmentDirections
                        .actionViewPathFragmentToPathDetailFragment(path))
                    viewPathViewModel.onPathDetailNavigated()
            }
        })

        val manager = GridLayoutManager(activity, 1)
        binding.pathList.layoutManager = manager

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