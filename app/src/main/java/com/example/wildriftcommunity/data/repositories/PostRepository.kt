package com.example.wildriftcommunity.data.repositories

import com.example.wildriftcommunity.data.models.Post
import com.example.wildriftcommunity.util.FirebaseSource

class PostRepository(private val firebase: FirebaseSource){

    fun createPost(post: Post) = firebase.createPost(post)

}