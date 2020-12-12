package com.example.wildriftcommunity.post.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.wildriftcommunity.ProgressListener
import com.example.wildriftcommunity.R
import com.example.wildriftcommunity.data.models.Post
import com.example.wildriftcommunity.databinding.ActivityPostInfoBinding
import com.example.wildriftcommunity.post.viewmodel.PostViewModel
import com.example.wildriftcommunity.post.viewmodel.PostViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class PostInfoActivity : AppCompatActivity(), ProgressListener, KodeinAware {

    override val kodein by kodein()
    private val factory: PostViewModelFactory by instance()
    private lateinit var postViewModel: PostViewModel
    private lateinit var binding : ActivityPostInfoBinding

    var postData: Post? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        postData = intent.getParcelableExtra<Post>("postData")

        postViewModel = ViewModelProvider(this, factory).get(PostViewModel::class.java)
        postViewModel.progressListener = this

        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_info)
        binding.profileViewModel = postViewModel
        binding.lifecycleOwner = this

        binding.apply {
          
        }

    }

    override fun onStarted() {
        binding.progressbarPostInfo.visibility = View.VISIBLE
    }

    override fun onSuccess(message: String) {
        binding.progressbarPostInfo.visibility = View.GONE
    }

    override fun onFailure(message: String) {
        binding.progressbarPostInfo.visibility = View.GONE
    }
}