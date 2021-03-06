package com.example.wildriftcommunity.data.repositories

import com.example.wildriftcommunity.util.FirebaseSource

class ChatRepository(private val firebase: FirebaseSource){

    fun getChatRoomId() = firebase.chatRoomId

    fun findRoomId(destinationUid: String) = firebase.findRoomId(destinationUid)

    fun createChatRoom(destinationUid: String) = firebase.createChatRoom(destinationUid)

    fun sendMessage(chatRoomID: String, message: String) = firebase.sendMessage(chatRoomID, message)

    fun alarm(destinationUid: String, kind: Int) = firebase.alarm(destinationUid, kind)

}