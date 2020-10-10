package com.example.wildriftcommunity.main

import android.R
import android.graphics.drawable.Drawable
import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainViewModel() : ViewModel(), BottomNavigationView.OnNavigationItemSelectedListener{

    enum class TAB {
        POST, CREATE_POST, NOTICE, CHAT, PROFILE
    }

    //Fragment
    private val _postFragment = MutableLiveData<Drawable>()
    val postFragment : LiveData<Drawable>
        get() = _postFragment

    private val _createPostActivity = MutableLiveData<Drawable>()
    val createPostActivity : LiveData<Drawable>
        get() = _createPostActivity

    private val _noticeFragment = MutableLiveData<Drawable>()
    val noticeFragment : LiveData<Drawable>
        get() = _noticeFragment

    private val _chatFragment = MutableLiveData<Drawable>()
    val chatFragment : LiveData<Drawable>
        get() = _chatFragment

    private val _profileFragment = MutableLiveData<Drawable>()
    val profileFragment : LiveData<Drawable>
        get() = _profileFragment

    //Title Text
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

        }
        return true
    }

    private fun resetTabs() {

        _postTabText.value = ""
        _noticeTabText.value = ""
        _chatTabText.value = ""
        _profileTabText.value = ""
    }



}