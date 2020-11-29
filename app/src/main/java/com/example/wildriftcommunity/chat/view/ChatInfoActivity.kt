package com.example.wildriftcommunity.chat.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.wildriftcommunity.ProgressListener
import com.example.wildriftcommunity.R
import com.example.wildriftcommunity.chat.viewmodel.ChatViewModel
import com.example.wildriftcommunity.chat.viewmodel.ChatViewModelFactory
import com.example.wildriftcommunity.databinding.ActivityChatInfoBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ChatInfoActivity : AppCompatActivity(), ProgressListener, KodeinAware {
    //mcxncSzaKoOgYq2Rfc9JEVHqCSI3 chosanghyun7
    override val kodein by kodein()
    private val factory: ChatViewModelFactory by instance()
    private lateinit var chatViewModel: ChatViewModel
    private lateinit var binding : ActivityChatInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        chatViewModel = ViewModelProvider(this, factory).get(ChatViewModel::class.java)
        chatViewModel.progressListener = this

        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat_info)
        binding.chatViewModel = chatViewModel
        binding.lifecycleOwner = this
    }

    override fun onStarted() {
        binding.progressbarChatInfo.visibility = View.VISIBLE
    }

    override fun onSuccess(message: String) {
        binding.progressbarChatInfo.visibility = View.GONE
    }

    override fun onFailure(message: String) {
        binding.progressbarChatInfo.visibility = View.GONE
    }

}