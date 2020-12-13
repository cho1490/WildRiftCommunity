package com.example.wildriftcommunity.post.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wildriftcommunity.ProgressListener
import com.example.wildriftcommunity.R
import com.example.wildriftcommunity.data.models.Post
import com.example.wildriftcommunity.post.adapter.PostListAdapter
import com.example.wildriftcommunity.post.viewmodel.PostViewModel
import com.example.wildriftcommunity.post.viewmodel.PostViewModelFactory
import kotlinx.android.synthetic.main.post_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import org.kodein.di.android.x.kodein

class PostFragment : Fragment(), ProgressListener, KodeinAware {

    override val kodein by kodein()
    private lateinit var postViewModel: PostViewModel
    private val factory: PostViewModelFactory by instance()
    lateinit var postList: List<Post>

    private var _postType = MutableLiveData<String>()
    private val postType: LiveData<String>
        get() = _postType

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.post_fragment, container,false)
        postViewModel = ViewModelProvider(this, factory).get(PostViewModel::class.java)
        postViewModel.progressListener = this

        postViewModel.startGetPost.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                postList = postViewModel.getPostList()
                rv_postFragmentPostList.apply {
                    layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                    setHasFixedSize(true)
                    adapter = PostListAdapter(postList)
                }
            }

        })

        postType.observe(viewLifecycleOwner, Observer {
            postViewModel.setPostList(postType.value.toString())
        })

        return view
    }

    override fun onStart() {
        super.onStart()
        _postType.value = "Free"
        postViewModel.setPostList(postType.value.toString())

        btn_postFragmentFree.setOnClickListener {
            _postType.value = "Free"
        }
        btn_postFragmentDuo.setOnClickListener {
            _postType.value = "Duo"
        }
    }

    override fun onStarted() {
        progressbarPost.visibility = View.VISIBLE
    }

    override fun onSuccess(message: String) {
        progressbarPost.visibility = View.GONE
    }

    override fun onFailure(message: String) {
        progressbarPost.visibility = View.GONE
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

}