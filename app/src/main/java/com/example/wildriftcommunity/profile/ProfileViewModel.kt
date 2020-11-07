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

    private val _startProfileEdit = MutableLiveData<Boolean>()
    val startProfileEdit: LiveData<Boolean>
        get() = _startProfileEdit


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

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

}