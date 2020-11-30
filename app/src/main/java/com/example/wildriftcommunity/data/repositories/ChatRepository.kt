package com.example.wildriftcommunity.data.repositories

import com.example.wildriftcommunity.util.FirebaseSource

class ChatRepository(private val firebase: FirebaseSource){

    fun checkChatRoom(destinationUid: String) = firebase.checkChatRoom(destinationUid)

}