package com.example.wildriftcommunity.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wildriftcommunity.ProgressListener
import com.example.wildriftcommunity.data.models.User
import com.example.wildriftcommunity.data.repositories.ProfileRepository

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ProfileViewModel(private val profileRepository: ProfileRepository) : ViewModel() {

    private val disposables = CompositeDisposable()
    var progressListener: ProgressListener? = null

    private val _photoUri = MutableLiveData<Uri>()
    val photoUri: LiveData<Uri>
        get() = _photoUri

    private val _nickname = MutableLiveData<String>()
    val nickname: LiveData<String>
        get() = _nickname

    private val _introduce = MutableLiveData<String>()
    val introduce: LiveData<String>
        get() = _introduce

    private val _userDetails = MutableLiveData<User>(User())
    val userDetails: LiveData<User>
        get() = _userDetails

    private val _startProfile = MutableLiveData<Boolean>()
    val startProfile: LiveData<Boolean>
        get() = _startProfile

    private val _startProfileEdit = MutableLiveData<Boolean>()
    val startProfileEdit: LiveData<Boolean>
        get() = _startProfileEdit

    private var _startPickImage = MutableLiveData<Boolean>()
    val startPickImage: LiveData<Boolean>
        get() = _startPickImage

    private var _startUpdate = MutableLiveData<Boolean>()
    val startUpdate: LiveData<Boolean>
        get() = _startUpdate

    fun getCurrentUserUid() = profileRepository.getCurrentUserUid()

    fun fetchUserDetails(destinationUid: String?) {
        progressListener?.onStarted()
        val disposable = profileRepository.fetchUserDetails(destinationUid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _userDetails.value = profileRepository.currentUserDetails()
                progressListener?.onSuccess("")
            }, {
                progressListener?.onFailure(it.message!!)
            })
        disposables.add(disposable)
    }

    fun setUpdateUserDetails(photoUri: Uri?, nickname: String, introduce: String) {
        _photoUri.value = photoUri
        _nickname.value = nickname
        _introduce.value = introduce
    }

    fun updateUserDetails(){
        _startUpdate.value = true
        _startUpdate.value = false
        progressListener?.onStarted()
        val disposable = profileRepository.updateUserDetails(photoUri.value, nickname.value!!, introduce.value!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                progressListener?.onSuccess("업데이트 완료!")
                _startProfile.value = true
                _startProfile.value = false
            },{
                progressListener?.onFailure("업데이트 실패!")
            })
        disposables.add(disposable)
    }

    fun goToProfileEdit(){
        _startProfileEdit.value = true
        _startProfileEdit.value = false
    }

    fun goToPickImage(){
        _startPickImage.value = true
        _startPickImage.value = false
    }

    fun goToProfile(){
        _startProfile.value = true
        _startProfile.value = false
    }

    fun alarm(destinationUid: String, kind: Int){
        val disposable = profileRepository.alarm(destinationUid, kind)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                progressListener?.onSuccess("")
            },{
                progressListener?.onFailure("")
            })
        disposables.add(disposable)
    }

    fun thumbsUpClick(destinationUid: String){

    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

}