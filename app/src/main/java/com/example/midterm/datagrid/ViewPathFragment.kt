package com.example.midterm.datagrid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.midterm.R
import com.example.midterm.database.PathDatabase
import com.example.midterm.databinding.FragmentViewPathBinding
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
}