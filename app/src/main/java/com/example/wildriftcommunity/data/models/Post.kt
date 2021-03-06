package com.example.wildriftcommunity.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Post(
    var type: String? = null,
    var title: String? = null,
    var body: String? = null,
    var imageUrl: String? = null,
    var timestamp: Long? = null,
    var userUid: String? = null,
    var favoriteCount: Int = 0,
    var favorites: MutableMap<String, Boolean> = HashMap()): Parcelable
{
    @Parcelize
    data class Comment(
        var uid: String? = null,
        var comment: String? = null,
        var timestamp: Long? = null): Parcelable
}