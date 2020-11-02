package com.example.wildriftcommunity.post.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wildriftcommunity.R
import com.example.wildriftcommunity.data.models.Post
import kotlinx.android.synthetic.main.post_list_item.view.*

class PostListAdapter(private val list: List<Post>): RecyclerView.Adapter<PostListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bind(item: Post){
            itemView.apply {
                Glide.with(this).load(item.userInfo!!.photoUri).into(iv_postListItemProfileImage) // profileImage
                tv_postListItemNickname.text = item.userInfo!!.nickname // nickname
                tv_postListItemTime.text = item.timestamp.toString() // timeStamp
                tv_postListItemTitle.text = item.title // title
                tv_postListItemBody.text = item.body // body
                Glide.with(this).load(item.imageUrl).into(iv_postListItemBodyImage) // photo
            }
        }
    }

}