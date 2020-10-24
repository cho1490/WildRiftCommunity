package com.example.wildriftcommunity.data.repositories

import android.net.Uri
import com.example.wildriftcommunity.util.FirebaseSource

class PostRepository(private val firebase: FirebaseSource){

    fun createPost(title: String, body: String, photoUri: Uri?) = firebase.createPost(title, body, photoUri)

}