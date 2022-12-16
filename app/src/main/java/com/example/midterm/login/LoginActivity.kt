package com.example.midterm.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.midterm.MainActivity
import com.example.midterm.R
import com.example.midterm.database.UserDatabase
import com.example.midterm.databinding.ActivityLoginBinding
import com.example.midterm.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val binding = DataBindingUtil.setContentView<ActivityLoginBinding>(this,R.layout.activity_login)

        val application = requireNotNull(this).application

        val dataSource = UserDatabase.getInstance(application).userDatabaseDao

        val viewModelFactory = LoginViewModelFactory(dataSource, application)

        val loginViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(LoginViewModel::class.java)

        binding.btnReg.setOnClickListener { view: View ->
            startActivity(Intent(this,RegisterActivity::class.java))
        }

        loginViewModel.navigateToHome.observe(this, Observer {
            startActivity(Intent(this,MainActivity::class.java))
        })

        binding.loginViewModel = loginViewModel
    }
}