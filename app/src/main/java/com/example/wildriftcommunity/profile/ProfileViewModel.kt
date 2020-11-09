package com.example.wildriftcommunity.profile

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

    fun fetchUserDetail() {
        progressListener?.onStarted()
        val disposable = profileRepository.fetchUserDetails()
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

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

}