package com.example.wildriftcommunity.data.models

data class Chat(
    val users: MutableMap<String, Boolean>? = null,
    val comments: MutableMap<String, Comment>? = null
){
    data class Comment(
        var uid: String? = null,
        var message: String? = null
    )
}