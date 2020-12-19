package com.example.wildriftcommunity.post.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wildriftcommunity.R
import com.example.wildriftcommunity.data.models.Post
import com.example.wildriftcommunity.data.models.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.comment_list_item.view.*

class CommentListAdapter(postId: String): RecyclerView.Adapter<CommentListAdapter.ViewHolder>(){

    private val list: ArrayList<Post.Comment> = arrayListOf()

    init {
        FirebaseFirestore.getInstance()
            .collection("posts")
            .document(postId)
            .collection("comments").get().addOnCompleteListener {
                for (snapshot in it.result!!)
                    list.add(snapshot.toObject(Post.Comment::class.java))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var user: User? = null
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(list[position].uid!!).get().addOnCompleteListener {
            user = it.result!!.toObject(User::class.java)
                holder.itemView.apply {
                    commentListItemProfileImage 
                    commentListItemNickname
                    commentListItemComment
                    commentListItemTime
            }
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

}