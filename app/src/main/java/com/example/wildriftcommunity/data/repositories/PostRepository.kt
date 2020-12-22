package com.example.wildriftcommunity.data.repositories

import android.net.Uri
import com.example.wildriftcommunity.util.FirebaseSource

class PostRepository(private val firebase: FirebaseSource){

    fun createPost(type:String, title: String, body: String, photoUri: Uri?) = firebase.createPost(type, title, body, photoUri)

    fun setPostList(type: String) = firebase.setPostList(type)

    fun getPostIdList() = firebase.postIdList

    fun setPostInfoInPost(postId: String) = firebase.setPostInfoInPost(postId)

    fun setUserInfoInPost(userUid: String) = firebase.setUserInfoInPost(userUid)

    fun getPostInfoInPost() = firebase.postInfoInPost

    fun getUserInfoInPost() = firebase.userInfoInPost

    fun sendComment(postId: String, messageBody: String) = firebase.sendComment(postId, messageBody)

}