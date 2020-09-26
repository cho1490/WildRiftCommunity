package com.example.wildriftcommunity.chat

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wildriftcommunity.R

class ChattingListFragment : Fragment() {

    companion object {
        fun newInstance() = ChattingListFragment()
    }

    private lateinit var viewModel: ChattingListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.chatting_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChattingListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}