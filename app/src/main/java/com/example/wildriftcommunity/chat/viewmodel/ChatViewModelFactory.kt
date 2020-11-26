package com.example.wildriftcommunity.chat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wildriftcommunity.data.repositories.ChatRepository
import com.example.wildriftcommunity.post.viewmodel.PostViewModel

@Suppress("UNCHECKED_CAST")
class ChatViewModelFactory(private val chatRepository: ChatRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChatViewModel(
            chatRepository
        ) as T
    }

}