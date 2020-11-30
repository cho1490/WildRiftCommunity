package com.example.wildriftcommunity.chat.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wildriftcommunity.ProgressListener
import com.example.wildriftcommunity.data.repositories.ChatRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ChatViewModel(private val chatRepository: ChatRepository) : ViewModel() {

    private val disposables = CompositeDisposable()
    var progressListener : ProgressListener? = null

//    private var _startCheckChatRoom = MutableLiveData<String>()
 //   val startCheckChatRoom: LiveData<String>
  //      get() = _startCheckChatRoom

    fun checkChatRoom(destinationUid: String) {
        progressListener?.onStarted()
        val disposable = chatRepository.checkChatRoom(destinationUid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                progressListener?.onSuccess("")
            }, {
                progressListener?.onFailure(it.message!!)
            })
        disposables.add(disposable)
    }

}