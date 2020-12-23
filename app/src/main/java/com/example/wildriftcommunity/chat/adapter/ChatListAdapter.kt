package com.example.wildriftcommunity.chat.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.wildriftcommunity.R
import com.example.wildriftcommunity.chat.view.ChatInfoActivity
import com.example.wildriftcommunity.data.models.Chat
import com.example.wildriftcommunity.data.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.chat_list_item.view.*
import java.util.*
import kotlin.collections.ArrayList

class ChatListAdapter(): RecyclerView.Adapter<ChatListAdapter.ViewHolder>(){

    private val list: ArrayList<Chat> = arrayListOf()
    private val myUid: String? = FirebaseAuth.getInstance().uid

    init {
        val chatListListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list.clear()
                for(item in dataSnapshot.children){
                    list.add(item.getValue(Chat::class.java)!!)
                }
                notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }

        FirebaseDatabase.getInstance().reference.child("chatRooms").orderByChild("users/$myUid").equalTo(true).addListenerForSingleValueEvent(chatListListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var destinationUid: String? = null
        for(user in list[position].users.keys){
            if(user != myUid)
                destinationUid = user
        }

        var user: User? = null
        FirebaseFirestore.getInstance().collection("users").document(destinationUid!!).get().addOnCompleteListener {
            user = it.result!!.toObject(User::class.java)!!
            Glide.with(holder.itemView.iv_chatListItemProfileImage.context)
                .load(user!!.photoUri)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.itemView.iv_chatListItemProfileImage)

            holder.itemView.tv_chatListItemNickname.text = user!!.nickname

            val commentMap: TreeMap<String, Chat.Comment> = TreeMap(Collections.reverseOrder())
            commentMap.putAll(list[position].comments)
            val lastIndexKey: String = commentMap.lastKey()
            holder.itemView.tv_chatListItemChat.text = list[position].comments[lastIndexKey]!!.message
        }

        holder.itemView.setOnClickListener {
            var roomID: String?
            val chatRoomIDListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var chat: Chat?
                    for(item in dataSnapshot.children){
                        chat = item.getValue(Chat::class.java)
                        if(chat!!.users.contains(destinationUid)){
                            roomID = item.key
                            val context=holder.itemView.context
                            val intent = Intent(context, ChatInfoActivity::class.java).apply { putExtra("roomID", roomID) }
                            context.startActivity(intent)
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                }
            }

            FirebaseDatabase.getInstance().reference.child("chatRooms").orderByChild("users/$myUid").equalTo(true).addListenerForSingleValueEvent(chatRoomIDListener)
        }

    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

}
