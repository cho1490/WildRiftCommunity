package com.example.wildriftcommunity.post.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.wildriftcommunity.R
import com.example.wildriftcommunity.data.models.Post
import com.example.wildriftcommunity.data.models.User
import com.example.wildriftcommunity.post.view.PostFragment
import com.example.wildriftcommunity.post.view.PostInfoActivity
import com.example.wildriftcommunity.util.timeConverter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.post_list_item.view.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class PostListAdapter(private val idList: List<String>, private val pf: PostFragment, private val glideRequestManager: RequestManager): RecyclerView.Adapter<PostListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return idList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        var post: Post? = null
        var user: User? = null

        db.collection("posts").document(idList[position]).get().addOnCompleteListener { postSnapshot ->
            post = postSnapshot.result?.toObject(Post::class.java)
            db.collection("users").document(post!!.userUid!!).get().addOnCompleteListener { userSnapshot ->
                user = userSnapshot.result?.toObject(User::class.java)!!

                holder.itemView.apply {
                    glideRequestManager.load(user!!.photoUri).into(profileImage)
                    nickname.text = user!!.nickname
                    time.text = timeConverter(post!!.timestamp.toString()) // timeStamp
                    title.text = post!!.title // title
                    body.text = post!!.body // body
                    glideRequestManager.load(post!!.imageUrl).into(bodyImage) // photo

                    setOnClickListener {
                        val intent = Intent(holder.itemView.context, PostInfoActivity::class.java)
                        intent.apply {
                            putExtra("postId", idList[position])
                            putExtra("userUid", post!!.userUid)
                        }
                        pf.activity!!.startActivityForResult(intent, 500)
                    }
                }
            }
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

}