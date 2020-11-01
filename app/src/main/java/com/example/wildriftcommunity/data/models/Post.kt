package com.example.wildriftcommunity.data.models

data class Post(
    var type: String? = null,
    var title: String? = null,
    var body: String? = null,
    var imageUrl: String? = null,
    var timestamp: Long? = null,
    var userInfo: User? = null,
    var favoriteCount: Int = 0,
    var favorites: MutableMap<String, Boolean> = HashMap()){

    data class Comment(
        var uid: String? = null,
        var nickname: String?,
        var comment: String? = null,
        var timestamp: Long? = null)
}