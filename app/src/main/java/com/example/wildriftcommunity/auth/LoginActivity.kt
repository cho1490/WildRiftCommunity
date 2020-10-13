package com.example.wildriftcommunity.auth

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
import com.example.wildriftcommunity.util.startHomeActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class LoginActivity() : AppCompatActivity(), ProgressListener, KodeinAware {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()
    private lateinit var viewModel : AuthViewModel
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        viewModel.progressListener = this
        binding.lifecycleOwner = this

        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        binding.authViewModel = viewModel

        viewModel.startLogin.observe(this, Observer {
            if (it == true) {
                viewModel.setLoginValues(
                    binding.email.text.toString(),
                    binding.password.text.toString()
                )
            }
        })

    }

    override fun onStarted() {
        progressbar.visibility = View.VISIBLE
    }

    override fun onSuccess() {
        progressbar.visibility = View.GONE
        startHomeActivity()
    }

    override fun onFailure(message: String) {
        progressbar.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}