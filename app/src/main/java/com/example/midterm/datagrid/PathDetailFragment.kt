package com.example.midterm.datagrid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.midterm.R
import com.example.midterm.database.PathDatabase
import com.example.midterm.databinding.FragmentPathDetailBinding

class PathDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentPathDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_path_detail, container, false)

        val application = requireNotNull(this.activity).application
        val arguments = PathDetailFragmentArgs.fromBundle(requireArguments())

        val dataSource = PathDatabase.getInstance(application).pathDatabaseDao
        val viewModelFactory = PathDetailViewModelFactory(arguments.pathKey,dataSource)

        val pathDetailViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(PathDetailViewModel::class.java)

        binding.pathDetailViewModel = pathDetailViewModel

        binding.setLifecycleOwner(this)

        pathDetailViewModel.navigateToViewPath.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    PathDetailFragmentDirections.actionPathDetailFragmentToViewPathFragment())
                pathDetailViewModel.doneNavigating()
            }
        })

        return binding.root
    }
}