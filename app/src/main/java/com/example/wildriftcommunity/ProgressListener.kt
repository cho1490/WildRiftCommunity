package com.example.wildriftcommunity

interface ProgressListener {

    fun onStarted()
    fun onSuccess(message: String)
    fun onFailure(message: String)

}