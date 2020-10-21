package com.example.wildriftcommunity.post

import androidx.lifecycle.ViewModel
import com.example.wildriftcommunity.ProgressListener
import com.example.wildriftcommunity.data.repositories.PostRepository

class PostViewModel(private val postRepository: PostRepository) : ViewModel() {

    var progressListener : ProgressListener? = null

}