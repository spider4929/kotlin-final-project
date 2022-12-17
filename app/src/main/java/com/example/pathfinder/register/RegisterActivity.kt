package com.example.pathfinder.register

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pathfinder.R
import com.example.pathfinder.database.UserDatabase
import com.example.pathfinder.databinding.ActivityRegisterBinding
import com.example.pathfinder.login.LoginActivity

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

        registerViewModel._error.observe(this, Observer { message ->
            Toast.makeText(this.applicationContext, message, Toast.LENGTH_SHORT).show()
        })

        registerViewModel._success.observe(this, Observer { message ->
            Toast.makeText(this.applicationContext, message, Toast.LENGTH_SHORT).show()
        })

        binding.registerViewModel = registerViewModel
    }
}