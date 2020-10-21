package com.example.wildriftcommunity.data.models

data class User(
    val email: String = "",
    val nickname: String = "",
    val introduce: String = "",
    val photoUri: String = "",
    val postCount: Int = 0,
    val lickCount: Int = 0,
    val kindScore: Int = 0
)