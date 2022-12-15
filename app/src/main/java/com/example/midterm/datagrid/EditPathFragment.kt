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
import com.example.midterm.R
import com.example.midterm.database.PathDatabase
import com.example.midterm.databinding.FragmentEditPathBinding

class EditPathFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentEditPathBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_edit_path,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val arguments = EditPathFragmentArgs.fromBundle(requireArguments())

        val dataSource = PathDatabase.getInstance(application).pathDatabaseDao
        val viewModelFactory = EditPathViewModelFactory(arguments.pathKey, dataSource)

        val editPathViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(EditPathViewModel::class.java)

        binding.editPathViewModel = editPathViewModel
        binding.setLifecycleOwner(this)

        editPathViewModel._navigateToViewPath.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    EditPathFragmentDirections.actionEditPathFragmentToViewPathFragment())
                editPathViewModel.doneNavigating()
            }
        })
        editPathViewModel.setData()

        return binding.root
    }

}