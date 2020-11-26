package com.example.wildriftcommunity.data.models


object MessageType{
    const val TEXT = "TEXT"
    const val IMAGE = "IMAGE"
}

interface Message {
    val timestamp: Long
    val uid: String
    val type: String
}