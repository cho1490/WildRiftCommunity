package com.example.wildriftcommunity.chat.viewmodel

import androidx.lifecycle.ViewModel
import com.example.wildriftcommunity.ProgressListener
import com.example.wildriftcommunity.data.repositories.ChatRepository

class ChatViewModel(private val chatRepository: ChatRepository) : ViewModel() {

    var progressListener : ProgressListener? = null
}