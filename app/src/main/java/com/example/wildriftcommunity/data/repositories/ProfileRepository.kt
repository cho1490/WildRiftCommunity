package com.example.wildriftcommunity.data.repositories

import android.net.Uri
import com.example.wildriftcommunity.util.FirebaseSource

class ProfileRepository (private val firebase: FirebaseSource) {

    fun currentUserDetails() = firebase.userDetails

    fun fetchUserDetails() = firebase.fetchUserDetails()

    fun updateUserDetails(photoUri: Uri?, nickname: String, introduce: String) = firebase.updateUserDetails(photoUri, nickname, introduce)

}