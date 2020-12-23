package com.example.wildriftcommunity.data.repositories

import com.example.wildriftcommunity.util.FirebaseSource

class AuthRepository(private val firebase: FirebaseSource) {

    fun login(email: String, password: String) = firebase.login(email, password)

    fun register(email: String, password: String) = firebase.register(email, password)

    fun currentUser() = firebase.currentUser()

    fun createUser(email: String) = firebase.createUser(email)

    fun alarm(destinationUid: String, kind: Int) = firebase.alarm(destinationUid, kind)

}