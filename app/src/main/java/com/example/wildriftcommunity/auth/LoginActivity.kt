package com.example.wildriftcommunity.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.wildriftcommunity.ProgressListener
import com.example.wildriftcommunity.R
import com.example.wildriftcommunity.databinding.ActivityLoginBinding
import com.example.wildriftcommunity.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class LoginActivity: AppCompatActivity(), ProgressListener, KodeinAware {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()
    private lateinit var authViewModel : AuthViewModel
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authViewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        authViewModel.progressListener = this

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.authViewModel = authViewModel
        binding.lifecycleOwner = this

        authViewModel.startLogin.observe(this, Observer {
            if (it == true) {
                authViewModel.setLoginValues(
                    binding.email.text.toString(),
                    binding.password.text.toString()
                )
            }
        })

    }

    override fun onStarted() {
        progressbarLogin.visibility = View.VISIBLE
    }

    override fun onSuccess(message: String) {
        progressbarLogin.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        startActivity(Intent(this,  MainActivity::class.java))
    }

    override fun onFailure(message: String) {
        progressbarLogin.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}