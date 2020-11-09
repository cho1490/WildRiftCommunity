package com.example.wildriftcommunity.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val email: String = "",
    val nickname: String = "",
    val introduce: String = "",
    val photoUri: String = "",
    val postCount: Int = 0,
    val lickCount: Int = 0,
    val kindScore: Int = 0
): Parcelable