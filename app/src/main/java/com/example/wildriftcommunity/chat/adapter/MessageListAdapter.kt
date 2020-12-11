package com.example.wildriftcommunity.chat.adapter

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wildriftcommunity.R
import com.example.wildriftcommunity.data.models.Chat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.message_list_item.view.*

class MessageListAdapter(roomID: String, recyclerView: RecyclerView): RecyclerView.Adapter<MessageListAdapter.ViewHolder>(){

    private var list: ArrayList<Chat.Comment> = arrayListOf()
    val myUid: String = FirebaseAuth.getInstance().currentUser!!.uid
    lateinit var destinationUid: String

    init {
        val messageListListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list.clear()
                for(item in dataSnapshot.children){
                    list.add(item.getValue(Chat.Comment::class.java)!!)
                }
                notifyDataSetChanged()
                recyclerView.scrollToPosition(list.size -1)
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }

        val userListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(item in dataSnapshot.children){
                    if (item.key.toString() != myUid)
                        destinationUid = item.key.toString()
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        FirebaseDatabase.getInstance().reference.child("chatRooms").apply {
            child(roomID).child("comments").addValueEventListener(messageListListener)
            child(roomID).child("users").addListenerForSingleValueEvent(userListener)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("RtlHardcoded")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(myUid == list[position].uid){
            holder.itemView.messageBody.setBackgroundResource(R.drawable.chat_right_buble)
            holder.itemView.mainLayout.gravity = Gravity.RIGHT
        }else{
            holder.itemView.messageBody.setBackgroundResource(R.drawable.chat_left_buble)
            holder.itemView.mainLayout.gravity = Gravity.LEFT
        }
        holder.itemView.messageBody.text = list[position].message
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

}