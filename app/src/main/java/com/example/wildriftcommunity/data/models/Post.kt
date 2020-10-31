package com.example.wildriftcommunity.data.models

data class Post(
    var title: String? = null,
    var body : String? = null,
    var imageUrl : String? = null,
    var uid : String? = null,
    var nickname : String? = null,
    var timestamp : Long? = null,
    var favoriteCount : Int = 0,
    var favorites : MutableMap<String, Boolean> = HashMap()){

    data class Comment(
        var uid : String? = null,
        var userId : String? = null,
        var comment  : String? = null,
        var timestamp : Long? = null)
}