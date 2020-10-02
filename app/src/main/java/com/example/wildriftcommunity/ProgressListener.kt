package com.example.wildriftcommunity

interface ProgressListener {

    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)

}