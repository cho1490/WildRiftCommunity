package com.example.wildriftcommunity.post.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.wildriftcommunity.R
import com.example.wildriftcommunity.data.models.Post
import com.example.wildriftcommunity.data.models.User
import com.example.wildriftcommunity.post.view.PostInfoActivity
import com.example.wildriftcommunity.util.timeConverter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.comment_list_item.view.*

class CommentListAdapter(postId: String, private val pia: PostInfoActivity): RecyclerView.Adapter<CommentListAdapter.ViewHolder>(){

    private val list: ArrayList<Post.Comment> = arrayListOf()
    private val fireStore = FirebaseFirestore.getInstance()

    init {
        fireStore
            .collection("posts")
            .document(postId)
            .collection("comments").orderBy("timestamp", Query.Direction.ASCENDING).addSnapshotListener { value, _ ->
                list.clear()
                if(value == null) return@addSnapshotListener

                for (snapshot in value.documents)
                    list.add(snapshot.toObject(Post.Comment::class.java)!!)
                notifyDataSetChanged()
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
        fireStore
            .collection("users")
            .document(list[position].uid!!).get().addOnCompleteListener {
                user = it.result!!.toObject(User::class.java)
                holder.itemView.apply {
                    if (pia.isFinishing)
                        return@addOnCompleteListener
                    Glide.with(pia).load(user!!.photoUri)
                        .apply(RequestOptions.circleCropTransform())
                        .into(commentListItemProfileImage)
                    commentListItemNickname.text = user!!.nickname
                    commentListItemComment.text = list[position].comment
                    commentListItemTime.text = timeConverter(list[position].timestamp.toString())

                    commentListItemProfileImage.setOnClickListener {
                        pia.finish()
                    }
                    commentListItemComment.setOnClickListener {
                        pia.finish()
                    }
                }
            }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

}