package com.example.wildriftcommunity.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.wildriftcommunity.R
import com.example.wildriftcommunity.auth.AuthViewModel
import com.example.wildriftcommunity.auth.AuthViewModelFactory
import com.example.wildriftcommunity.databinding.ActivityLoginBinding
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity() {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory
    private lateinit var viewModel : AuthViewModel
    private lateinit var binding : ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }


}