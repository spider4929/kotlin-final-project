package com.example.midterm.register

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.midterm.MainActivity
import com.example.midterm.R
import com.example.midterm.database.UserDatabase
import com.example.midterm.databinding.ActivityLoginBinding
import com.example.midterm.databinding.ActivityRegisterBinding
import com.example.midterm.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    @SuppressLint("FragmentLiveDataObserve")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val binding = DataBindingUtil.setContentView<ActivityRegisterBinding>(this,R.layout.activity_register)

        binding.button.setOnClickListener { view: View ->
            startActivity(Intent(this, LoginActivity::class.java))
        }

        val application = requireNotNull(this).application

        val dataSource = UserDatabase.getInstance(application).userDatabaseDao

        val viewModelFactory = RegisterViewModelFactory(dataSource, application)

        val registerViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(RegisterViewModel::class.java)

        binding.registerViewModel = registerViewModel
    }
}