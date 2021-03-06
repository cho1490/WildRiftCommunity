package com.example.wildriftcommunity.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var email: String = "",
    var nickname: String = "",
    var introduce: String = "",
    var photoUri: String = "",
    var postCount: Int = 0,
    var likeCount: Int = 0,
    var kindScore: Int = 0,
    var favorites : MutableMap<String, Boolean> = HashMap()
): Parcelable