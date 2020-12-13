package com.example.wildriftcommunity.post.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wildriftcommunity.R
import com.example.wildriftcommunity.data.models.Post
import com.example.wildriftcommunity.data.models.User
import com.example.wildriftcommunity.post.view.PostInfoActivity
import com.example.wildriftcommunity.util.timeConverter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.post_list_item.view.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class PostListAdapter(private val list: List<Post>): RecyclerView.Adapter<PostListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        var user: User? = null
        db.collection("users").document(list[position].userUid!!).get().addOnCompleteListener {
            user = it.result?.toObject(User::class.java)!!

            holder.itemView.apply {
                Glide.with(this).load(user!!.photoUri).into(profileImage)
                nickname.text = user!!.nickname
                time.text = timeConverter(list[position].timestamp.toString()) // timeStamp
                title.text = list[position].title // title
                body.text = list[position].body // body
                Glide.with(this).load(list[position].imageUrl).into(bodyImage) // photo

                setOnClickListener {
                    val intent = Intent(holder.itemView.context, PostInfoActivity::class.java)
                    intent.apply {
                        putExtra("postData", list[position])
                        putExtra("userUid", list[position].userUid)
                    }
                    ContextCompat.startActivity(holder.itemView.context, intent, null)
                }
            }
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

}