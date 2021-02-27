package com.example.wildriftcommunity.auth

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wildriftcommunity.ProgressListener
import com.example.wildriftcommunity.data.repositories.AuthRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AuthViewModel(private val authRepository : AuthRepository) : ViewModel() {

    var progressListener : ProgressListener? = null
    private val disposables = CompositeDisposable()

    private var _email = MutableLiveData<String>()
    val email : LiveData<String>
        get() = _email

    private var _password = MutableLiveData<String>()
    val password : LiveData<String>
        get() = _password

    private var _startLogin = MutableLiveData<Boolean>()
    val startLogin : LiveData<Boolean>
        get() = _startLogin

    private var _startSignUp = MutableLiveData<Boolean>()
    val startSignUp : LiveData<Boolean>
        get() = _startSignUp

    private var _startSetUp = MutableLiveData<Boolean>()
    val startSetUp : LiveData<Boolean>
        get() = _startSetUp

    private val _photoUri = MutableLiveData<Uri>()
    val photoUri: LiveData<Uri>
        get() = _photoUri

    private val _nickname = MutableLiveData<String>()
    val nickname: LiveData<String>
        get() = _nickname

    private val _introduce = MutableLiveData<String>()
    val introduce: LiveData<String>
        get() = _introduce

    private var _startPickImage = MutableLiveData<Boolean>()
    val startPickImage: LiveData<Boolean>
        get() = _startPickImage

    private var _startUpdate = MutableLiveData<Boolean>()
    val startUpdate: LiveData<Boolean>
        get() = _startUpdate

    private var _startMain = MutableLiveData<Boolean>()
    val startMain : LiveData<Boolean>
        get() = _startMain

    //LoginActivity
    fun login() {
        _startLogin.value = true
        _startLogin.value = false
        progressListener?.onStarted()

        val disposable = authRepository.login(email.value!!, password.value!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                progressListener?.onSuccess("로그인 성공!")
            }, {
                progressListener?.onFailure(it.message!!)
            })

        disposables.add(disposable)
    }

    fun setLoginValues(email: String, password: String) {
        _email.value = email
        _password.value = password
    }

    fun goToSignUp(view : View) {
        Intent(view.context, SignUpActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    //SignUpActivity
    fun register() {
        _startSignUp.value = true
        _startSignUp.value = false
        progressListener?.onStarted()

        val disposable = authRepository.register(email.value!!, password.value!!)
            .andThen(authRepository.login(email.value!!, password.value!!))
            .andThen(authRepository.createUser(email.value!!))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                progressListener?.onSuccess("가입 성공!")
                _startSetUp.value = true
            }, {
                progressListener?.onFailure(it.message!!)
                _startSetUp.value = false
            })

        disposables.add(disposable)
    }

    fun setRegisterValue(email: String, password: String){
        _email.value = email
        _password.value = password
    }

    //SetUpActivity
    fun setUpdateUserDetails(photoUri: Uri?, nickname: String, introduce: String) {
        _photoUri.value = photoUri
        _nickname.value = nickname
        _introduce.value = introduce
    }

    fun goToPickImage(){
        _startPickImage.value = true
        _startPickImage.value = false
    }

    fun updateUserDetails(){
        _startUpdate.value = true
        _startUpdate.value = false
        progressListener?.onStarted()
        val disposable = authRepository.updateUserDetails(photoUri.value, nickname.value!!, introduce.value!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                progressListener?.onSuccess("초기 설정 완료!")
                _startMain.value = true
                _startMain.value = false
            },{
                progressListener?.onFailure("초기 설정 실패!")
            })
        disposables.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

}