package com.example.wildriftcommunity.data.models

data class TextMessage(
    val text: String? = null,
    override var timestamp: Long = 0,
    override val uid: String = "",
    override val type: String = MessageType.TEXT
) : Message{
    constructor() : this("", 0,"")
}