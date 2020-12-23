package com.example.wildriftcommunity.data.repositories

import com.example.wildriftcommunity.util.FirebaseSource

class NoticeRepository(private val firebase: FirebaseSource){

    fun getCurrentUserUid() = firebase.currentUser()!!.uid

}