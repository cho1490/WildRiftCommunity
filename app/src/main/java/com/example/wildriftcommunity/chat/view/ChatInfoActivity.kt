package com.example.wildriftcommunity.chat.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wildriftcommunity.ProgressListener
import com.example.wildriftcommunity.R
import com.example.wildriftcommunity.chat.adapter.MessageRecyclerView
import com.example.wildriftcommunity.chat.viewmodel.ChatViewModel
import com.example.wildriftcommunity.chat.viewmodel.ChatViewModelFactory
import com.example.wildriftcommunity.data.models.Chat
import com.example.wildriftcommunity.databinding.ActivityChatInfoBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class ChatInfoActivity : AppCompatActivity(), ProgressListener, KodeinAware {

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

        val roomID = intent.getStringExtra("roomID")
        chatViewModel.setMessage(roomID!!)

        binding.sendMessage.setOnClickListener {
            chatViewModel.sendMessage(roomID, binding.sendMessageBody.text.toString())
        }

        chatViewModel.getMessage().observe(this, Observer {
            var list: ArrayList<Chat.Comment> = arrayListOf()
            for( (k, v) in it.comments){
                list.add(v)
            }
            binding.messageRecyclerView.apply {
                layoutManager = LinearLayoutManager(this@ChatInfoActivity, LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                adapter = MessageRecyclerView(list)
            }
        })

    }

    override fun onStarted() {
        binding.progressbarChatInfo.visibility = View.VISIBLE
    }

    override fun onSuccess(message: String) {
        binding.progressbarChatInfo.visibility = View.GONE
    }

    override fun onFailure(message: String) {
        binding.progressbarChatInfo.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}