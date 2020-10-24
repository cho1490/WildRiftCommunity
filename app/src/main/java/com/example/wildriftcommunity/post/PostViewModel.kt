package com.example.wildriftcommunity.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wildriftcommunity.ProgressListener
import com.example.wildriftcommunity.data.models.Post
import com.example.wildriftcommunity.data.repositories.PostRepository
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PostViewModel(private val postRepository: PostRepository) : ViewModel() {

    var progressListener : ProgressListener? = null
    private val disposables = CompositeDisposable()

    private var _title = MutableLiveData<String>()
    val title : LiveData<String>
        get() = _title

    private var _body = MutableLiveData<String>()
    val body : LiveData<String>
        get() = _body

    private var _photoUri = MutableLiveData<String>()
    val photoUri : LiveData<String>
        get() = _photoUri

    private var _startCreatePost = MutableLiveData<Boolean>()
    val startCreatePost : LiveData<Boolean>
        get() = _startCreatePost


    fun createPost() {
        _startCreatePost.value = true
        _startCreatePost.value = true
        progressListener?.onStarted()

        val post = Post(title.value!!, body.value!!)
        val disposable = postRepository.createPost(post)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                progressListener?.onSuccess("글 작성 완료!")
            }, {
                progressListener?.onFailure(it.message!!)
            })

        disposables.add(disposable)
    }

    fun setCreatePostValues(title: String, body: String) {
        _title.value = title
        _body.value = body
    }

}