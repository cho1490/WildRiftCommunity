package com.example.wildriftcommunity.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.wildriftcommunity.ProgressListener
import com.example.wildriftcommunity.R
import com.example.wildriftcommunity.auth.AuthViewModel
import com.example.wildriftcommunity.auth.AuthViewModelFactory
import com.example.wildriftcommunity.databinding.ActivityCreatePostBinding
import com.example.wildriftcommunity.databinding.ActivityCreatePostBindingImpl
import com.example.wildriftcommunity.databinding.ActivityLoginBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class CreatePostActivity : AppCompatActivity(), ProgressListener, KodeinAware {

    override val kodein by kodein()
    private val factory: PostViewModelFactory by instance()
    private lateinit var viewModel : PostViewModel
    private lateinit var binding : ActivityCreatePostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, factory).get(PostViewModel::class.java)
        viewModel.progressListener = this

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_post)
        binding.postViewModel = viewModel
        binding.lifecycleOwner = this

    }

    override fun onStarted() {
        TODO("Not yet implemented")
    }

    override fun onSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onFailure(message: String) {
        TODO("Not yet implemented")
    }

}