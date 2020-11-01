package com.example.wildriftcommunity.post.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.wildriftcommunity.ProgressListener
import com.example.wildriftcommunity.R
import com.example.wildriftcommunity.post.viewmodel.PostViewModel

class PostFragment : Fragment(), ProgressListener {

    private lateinit var postViewModel: PostViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        postViewModel = ViewModelProvider(this).get(PostViewModel::class.java)
        postViewModel.progressListener = this

        return inflater.inflate(R.layout.post_fragment, container, false)
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