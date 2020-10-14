package com.example.wildriftcommunity.auth

data class User(
    val email: String = "",
    val nickname: String = "",
    val photoUri: String = "",
    val postCount: Int = 0,
    val lickCount: Int = 0,
    val kindScore: Double = 0.0
)