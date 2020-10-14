package com.example.wildriftcommunity.auth

import android.content.Intent
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wildriftcommunity.ProgressListener
import com.google.firebase.firestore.FirebaseFirestore
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

    private var _passwordCheck = MutableLiveData<String>()
    val passwordCheck : LiveData<String>
        get() = _passwordCheck

    private var _nickname = MutableLiveData<String>()
    val nickname : LiveData<String>
        get() = _nickname

    private var _startLogin = MutableLiveData<Boolean>()
    val startLogin : LiveData<Boolean>
        get() = _startLogin

    private var _startSignUp = MutableLiveData<Boolean>()
    val startSignUp : LiveData<Boolean>
        get() = _startSignUp

    private var _checkLogin = MutableLiveData<Boolean>()
    val checkLogin : LiveData<Boolean>
        get() = _checkLogin

    //LoginActivity
    fun login() {
        _startLogin.value = true
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

        _startLogin.value = false
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
        progressListener?.onStarted()

        if(_checkLogin.value!!) {
            _checkLogin.value = false
            progressListener?.onStarted()
            val disposable = authRepository.register(email.value!!, password.value!!)
                .andThen(authRepository.login(email.value!!, password.value!!))
                .andThen(authRepository.saveUserDetails(email.value!!, nickname.value!!))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    progressListener?.onSuccess("가입 성공!")
                }, {
                    progressListener?.onFailure(it.message!!)
                })
            disposables.add(disposable)
        }else{
            progressListener?.onFailure("닉네임 중복 체크 필수!")
        }

        _startSignUp.value = false
    }

    fun setRegisterValue(email: String, password: String, passwordCheck: String, nickname: String){
        _email.value = email
        _password.value = password
        _passwordCheck.value = passwordCheck
        _nickname.value = nickname
    }

    fun checkNickname() {
        progressListener?.onStarted()


        _checkLogin.value = true
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

}