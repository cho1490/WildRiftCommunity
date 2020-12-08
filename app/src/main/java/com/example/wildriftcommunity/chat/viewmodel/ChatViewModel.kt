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

    private var _startChatInfo = MutableLiveData<Boolean>()
    val startChatInfo: LiveData<Boolean>
        get() = _startChatInfo

    fun getChatRoomId() = chatRepository.getChatRoomId()

    fun findRoomId(destinationUid: String) {
        progressListener?.onStarted()
        val disposable = chatRepository.findRoomId(destinationUid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if(getChatRoomId() == null){
                    _startChatInfo.value = false
                    progressListener?.onSuccess("")
                    //새로운 채팅방 생성
                }else{
                    _startChatInfo.value = true
                    progressListener?.onSuccess("채팅방 입장")
                }
            },{
                progressListener?.onFailure("방 찾기 에러")
            })
        disposables.add(disposable)
    }

    fun createChatRoom(destinationUid: String){
        progressListener?.onStarted()
        val disposable = chatRepository.createChatRoom(destinationUid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _startChatInfo.value = true
                progressListener?.onSuccess("채팅방 생성")
            }, {
                progressListener?.onFailure("채팅방 생성 에러")
            })
        disposables.add(disposable)
    }


    fun sendMessage(chatRoomID: String, message: String) {
        progressListener?.onStarted()
        val disposable = chatRepository.sendMessage(chatRoomID, message)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                progressListener?.onSuccess("")
            },{
                progressListener?.onFailure("메시지 입력 에러")
            })
    }

}