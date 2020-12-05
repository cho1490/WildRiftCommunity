package com.example.wildriftcommunity.chat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wildriftcommunity.R
import com.example.wildriftcommunity.data.models.Chat
import kotlinx.android.synthetic.main.message_list_item.view.*

class MessageRecyclerView(private val list: List<Chat.Comment>): RecyclerView.Adapter<MessageRecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bind(item: Chat.Comment){
            itemView.apply {

                messageBody.text = item.message
                //nickName.text = item.
            }
        }
    }

}