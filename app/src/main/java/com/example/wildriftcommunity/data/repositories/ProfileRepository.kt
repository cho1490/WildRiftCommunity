package com.example.wildriftcommunity.data.repositories

import com.example.wildriftcommunity.util.FirebaseSource

class ProfileRepository (private val firebase: FirebaseSource) {

    fun currentUserDetails() = firebase.userDetails

    fun fetchUserDetails() = firebase.fetchUserDetails()

}