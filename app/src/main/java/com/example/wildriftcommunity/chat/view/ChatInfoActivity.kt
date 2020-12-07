package com.example.wildriftcommunity.chat.view

import android.content.Intent
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
import kotlinx.android.synthetic.main.activity_chat_info.view.*
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

        var roomID = intent.getStringExtra("roomID")

        binding.sendMessage.setOnClickListener {
            chatViewModel.sendMessage(roomID!!, binding.sendMessageBody.text.toString())
            binding.sendMessageBody.setText("")
        }

        binding.messageRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ChatInfoActivity, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = MessageRecyclerView(roomID!!, messageRecyclerView)
        }


       // chatViewModel.getMessage().observe(this, Observer {
         //   binding.messageRecyclerView.apply {
           //     layoutManager = LinearLayoutManager(this@ChatInfoActivity, LinearLayoutManager.VERTICAL, false)
             //   setHasFixedSize(true)
               // adapter = MessageRecyclerView(it)
                //adapter!!.notifyDataSetChanged()
           // }
        //})

    }

    override fun onStart() {
        super.onStart()
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