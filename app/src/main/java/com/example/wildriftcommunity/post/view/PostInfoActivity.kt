package com.example.wildriftcommunity.post.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.wildriftcommunity.ProgressListener
import com.example.wildriftcommunity.R
import com.example.wildriftcommunity.data.models.Post
import com.example.wildriftcommunity.data.models.User
import com.example.wildriftcommunity.databinding.ActivityPostInfoBinding
import com.example.wildriftcommunity.post.adapter.CommentListAdapter
import com.example.wildriftcommunity.post.viewmodel.PostViewModel
import com.example.wildriftcommunity.post.viewmodel.PostViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class PostInfoActivity : AppCompatActivity(), ProgressListener, KodeinAware {

    override val kodein by kodein()
    private val factory: PostViewModelFactory by instance()
    private lateinit var postViewModel: PostViewModel
    private lateinit var binding : ActivityPostInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        postViewModel = ViewModelProvider(this, factory).get(PostViewModel::class.java)
        postViewModel.progressListener = this
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_info)
        binding.profileViewModel = postViewModel
        binding.lifecycleOwner = this

        val postId = intent.getStringExtra("postId")!!
        val userUid = intent.getStringExtra("userUid")!!

        var post: Post? = null

        postViewModel.setDataInfoInPost(postId, userUid)

        postViewModel.startPostInfo.observe(this, Observer {
            if (it == true){
                binding.apply {
                    post = postViewModel.getPostInfoInPost()!!
                    val user: User = postViewModel.getUserInfoInPost()!!
                    Glide.with(this@PostInfoActivity).load(user.photoUri).into(profileImage)
                    nickname.text = user.nickname
                    title.text = post!!.title
                    body.text = post!!.body
                    Glide.with(this@PostInfoActivity).load(post!!.imageUrl).into(bodyImage)
                }
            }
        })

        binding.commentRecyclerView.apply{
            layoutManager = LinearLayoutManager( this@PostInfoActivity, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(false)
            val glideRequestManager = Glide.with(this@PostInfoActivity)
            adapter = CommentListAdapter(postId, this@PostInfoActivity, glideRequestManager)
        }

        binding.sendComment.setOnClickListener {
            postViewModel.alarm(post!!.userUid!!, 0)
            postViewModel.sendMessage(postId, binding.sendMessageBody.text.toString())
            binding.sendMessageBody.setText("")
        }

    }

    override fun onStarted() {
        binding.progressbarPostInfo.visibility = View.VISIBLE
    }

    override fun onSuccess(message: String) {
        binding.progressbarPostInfo.visibility = View.GONE
    }

    override fun onFailure(message: String) {
        binding.progressbarPostInfo.visibility = View.GONE
    }


}