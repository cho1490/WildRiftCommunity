package com.example.wildriftcommunity.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.wildriftcommunity.ProgressListener
import com.example.wildriftcommunity.R
import com.example.wildriftcommunity.databinding.ActivityCreatePostBinding
import kotlinx.android.synthetic.main.activity_create_post.*
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

        viewModel.startCreatePost.observe(this, Observer{
            if (it == true){
                viewModel.setCreatePostValues(
                    binding.postTitle.text.toString(),
                    binding.postBody.text.toString()
                )
            }
        })

    }


    override fun onStarted() {
        progressbarCreatePost.visibility = View.VISIBLE
    }

    override fun onSuccess(message: String) {
        progressbarCreatePost.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onFailure(message: String) {
        progressbarCreatePost.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}