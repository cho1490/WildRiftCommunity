package com.example.wildriftcommunity.notice.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.wildriftcommunity.R
import com.example.wildriftcommunity.data.models.Alarm
import com.example.wildriftcommunity.data.models.User
import com.example.wildriftcommunity.notice.view.NoticeFragment
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.notice_list_item.view.*


class NoticeListAdapter(uid: String, private val nf: NoticeFragment): RecyclerView.Adapter<NoticeListAdapter.ViewHolder>() {

    private val list: ArrayList<Alarm> = arrayListOf()
    private val fireStore = FirebaseFirestore.getInstance()

    init {
        fireStore.collection("alarms").whereEqualTo("destinationUid", uid).addSnapshotListener { value, _ ->
            list.clear()
            if(value == null) return@addSnapshotListener

            for (snapshot in value.documents)
                list.add(snapshot.toObject(Alarm::class.java)!!)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notice_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var user: User? = null
        fireStore.collection("users").document(list[position].Uid!!).get().addOnCompleteListener {
            if (nf.isHidden)
                return@addOnCompleteListener

            user = it.result!!.toObject(User::class.java)!!

            var kindMessage: String? = null
            if (list[position].kind == 0){
                kindMessage = "님이 게시글에 댓글을 남겼습니다."
            }
            if (list[position].kind == 1){
                kindMessage = "님이 채팅을 신청하였습니다."
            }
            if (list[position].kind == 2){
                kindMessage = "좋아요를 눌렀습니다."
            }

            if (user!!.photoUri != "")
                Glide.with(nf).load(user!!.photoUri)
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.itemView.image)
            holder.itemView.message.text = user!!.nickname + " " + kindMessage
        }

    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

}