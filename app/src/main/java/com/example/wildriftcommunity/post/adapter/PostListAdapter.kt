package com.example.wildriftcommunity.post.adapter

import android.annotation.SuppressLint
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
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.post_list_item.view.*
import java.util.*

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
                    intent.putExtra("postData", list[position])
                    ContextCompat.startActivity(holder.itemView.context, intent, null)
                }
            }

        }

    }

    @SuppressLint("SimpleDateFormat")
    fun timeConverter(time:  String): String {
        val calendar: Calendar = Calendar.getInstance()

    //    val dateFormat = SimpleDateFormat("yyyy-mm-dd_HH:mm:ss")
  //      val date: Date = dateFormat.parse(time)

        val now: Long = calendar.timeInMillis
//        val dateM: Long = date.time
        var gap = now - time.toLong()

        //        초       분   시
        //        1000    60  60
        gap = (gap / 1000)
        val hour = gap / 3600
        gap %= 3600
        val min = gap / 60
        val sec = gap % 60

        when {
            hour > 720 -> {
                return (hour / 720).toString() + "달 전"
            }
            hour > 24 -> {
                return  (hour / 24).toString() + "일 전"
            }
            hour > 0 -> {
                return hour.toString() + "시간 전"
            }
            min > 0 -> {
                return min.toString() + "분 전"
            }
            sec > 0 -> {
                return sec.toString() + "초 전"
            }
            else -> {
                return "오래 전"
            }
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

}