package com.example.pathfinder.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pathfinder.MainActivity
import com.example.pathfinder.R
import com.example.pathfinder.database.UserDatabase
import com.example.pathfinder.databinding.ActivityLoginBinding
import com.example.pathfinder.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val binding = DataBindingUtil.setContentView<ActivityLoginBinding>(this,R.layout.activity_login)

        val application = requireNotNull(this).application
        val dataSource = UserDatabase.getInstance(application).userDatabaseDao
        val viewModelFactory = LoginViewModelFactory(dataSource, application)
        val loginViewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)

        binding.btnReg.setOnClickListener { view: View ->
            startActivity(Intent(this,RegisterActivity::class.java))
        }

        loginViewModel.navigateToHome.observe(this, Observer {
            startActivity(Intent(this,MainActivity::class.java))
        })

        loginViewModel._error.observe(this, Observer { message ->
            Toast.makeText(this.applicationContext, message, Toast.LENGTH_SHORT).show()
        })

        loginViewModel._success.observe(this, Observer { message ->
            Toast.makeText(this.applicationContext, message, Toast.LENGTH_SHORT).show()
        })

        binding.loginViewModel = loginViewModel
    }
}