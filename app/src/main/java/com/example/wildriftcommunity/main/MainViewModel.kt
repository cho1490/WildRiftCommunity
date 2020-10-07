package com.example.wildriftcommunity.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel() : ViewModel(){

    private var _postTabText = MutableLiveData<String>()
    val postTabText: LiveData<String>
        get() = _postTabText

    private var _noticeTabText = MutableLiveData<String>()
    val noticeTabText: LiveData<String>
        get() = _noticeTabText

    private var _chatTabText = MutableLiveData<String>()
    val chatTabText: LiveData<String>
        get() = _chatTabText

    private var _profileTabText = MutableLiveData<String>()
    val profileTabText: LiveData<String>
        get() = _profileTabText



}