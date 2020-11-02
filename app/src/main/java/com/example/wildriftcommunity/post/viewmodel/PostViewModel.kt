package com.example.wildriftcommunity.post.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wildriftcommunity.ProgressListener
import com.example.wildriftcommunity.data.repositories.PostRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PostViewModel(private val postRepository: PostRepository) : ViewModel() {

    var progressListener : ProgressListener? = null
    private val disposables = CompositeDisposable()

    private var _title = MutableLiveData<String>()
    val title: LiveData<String>
        get() = _title

    private var _body = MutableLiveData<String>()
    val body: LiveData<String>
        get() = _body

    private var _photoUri = MutableLiveData<Uri>()
    val photoUri: LiveData<Uri>
        get() = _photoUri

    private var _startPickImage = MutableLiveData<Boolean>()
    val startPickImage: LiveData<Boolean>
        get() = _startPickImage

    private var _startCreatePost = MutableLiveData<Boolean>()
    val startCreatePost: LiveData<Boolean>
        get() = _startCreatePost

    private var _startGetPost = MutableLiveData<Boolean>()
    val startGetPost: LiveData<Boolean>
        get() = _startGetPost

    fun createPost() {
        _startCreatePost.value = true
        _startCreatePost.value = false
        progressListener?.onStarted()

        val disposable = postRepository.createPost(title.value!!, body.value!!, photoUri.value!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                progressListener?.onSuccess("글 작성 완료!")
            }, {
                progressListener?.onFailure(it.message!!)
            })

        disposables.add(disposable)
    }

    fun setCreatePostValues(title: String, body: String, photoUri: Uri?) {
        _title.value = title
        _body.value = body
        _photoUri.value = photoUri
    }

    fun goToPickImage(){
        _startPickImage.value = true
        _startPickImage.value = false
    }

    fun setPostList(){
        progressListener?.onStarted()
        val disposable = postRepository.setPostList("Free")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                progressListener?.onSuccess("")
                _startGetPost.value = true
                _startGetPost.value = false
            }, {
                progressListener?.onFailure("글 가져오기 실패!")
            })

        disposables.add(disposable)
    }

    fun getPostList() = postRepository.getPostList()

}