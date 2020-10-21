package com.example.wildriftcommunity.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wildriftcommunity.data.repositories.PostRepository

@Suppress("UNCHECKED_CAST")
class PostViewModelFactory (private val postRepository: PostRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostViewModel(postRepository) as T
    }
}