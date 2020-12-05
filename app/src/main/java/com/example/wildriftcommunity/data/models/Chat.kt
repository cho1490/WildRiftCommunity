package com.example.wildriftcommunity.data.models

data class Chat(
    val users: MutableMap<String, Boolean> = mutableMapOf(),
    val comments: MutableMap<String, Comment> = mutableMapOf()
){
    data class Comment(
        var uid: String = "",
        var message: String = ""
    )
}