package com.example.wildriftcommunity.data.repositories

import android.net.Uri
import com.example.wildriftcommunity.util.FirebaseSource

class ProfileRepository (private val firebase: FirebaseSource) {

    fun currentUserDetails() = firebase.userInfoInProfile

    fun fetchUserDetails(destinationUid: String?) = firebase.fetchUserDetails(destinationUid)

    fun updateUserDetails(photoUri: Uri?, nickname: String, introduce: String) = firebase.updateUserDetails(photoUri, nickname, introduce)

    fun alarm(destinationUid: String, kind: Int) = firebase.alarm(destinationUid, kind)

    fun thumbsUpClick(destinationUid: String) = firebase.thumbsUpClick(destinationUid)
}