package com.example.wildriftcommunity.data.repositories

import android.net.Uri
import com.example.wildriftcommunity.util.FirebaseSource

class AuthRepository(private val firebase: FirebaseSource) {

    fun login(email: String, password: String) = firebase.login(email, password)

    fun register(email: String, password: String) = firebase.register(email, password)

    fun createUser(email: String) = firebase.createUser(email)

    fun updateUserDetails(photoUri: Uri?, nickname: String, introduce: String) = firebase.updateUserDetails(photoUri, nickname, introduce)

    fun alarm(destinationUid: String, kind: Int) = firebase.alarm(destinationUid, kind)

}