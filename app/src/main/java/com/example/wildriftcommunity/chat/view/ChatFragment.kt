package com.example.wildriftcommunity.chat.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wildriftcommunity.ProgressListener
import com.example.wildriftcommunity.R
import com.example.wildriftcommunity.chat.adapter.ChatListAdapter
import com.example.wildriftcommunity.chat.viewmodel.ChatViewModel
import com.example.wildriftcommunity.chat.viewmodel.ChatViewModelFactory
import com.example.wildriftcommunity.databinding.ChatFragmentBinding
import kotlinx.android.synthetic.main.chat_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import org.kodein.di.android.x.kodein

class ChatFragment : Fragment(), ProgressListener, KodeinAware {

    override val kodein by kodein()
    private lateinit var chatViewModel: ChatViewModel
    lateinit var binding: ChatFragmentBinding
    private val factory: ChatViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        chatViewModel = ViewModelProvider(this, factory).get(ChatViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.chat_fragment, container, false)
        binding.lifecycleOwner = activity
        binding.chatViewModel = chatViewModel
        chatViewModel.progressListener = this

        binding.chatListRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = ChatListAdapter()
        }

        chatViewModel.startChatInfo.observe(viewLifecycleOwner, Observer {
            if(it){
                startActivity(Intent(activity, ChatInfoActivity::class.java).apply { putExtra("roomID", chatViewModel.getChatRoomId()) })
            }else{
                chatViewModel.createChatRoom("mcxncSzaKoOgYq2Rfc9JEVHqCSI3")
            }
        })

        return binding.root
    }

    override fun onStarted() {
        progressbarChat.visibility = View.VISIBLE
    }

    override fun onSuccess(message: String) {
        progressbarChat.visibility = View.GONE
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onFailure(message: String) {
        progressbarChat.visibility = View.GONE
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

}