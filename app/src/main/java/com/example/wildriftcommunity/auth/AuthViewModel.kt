package com.example.wildriftcommunity.auth

import android.content.Intent
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wildriftcommunity.ProgressListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AuthViewModel(private val authRepository : AuthRepository) : ViewModel() {

    var progressListener : ProgressListener? = null
    private val disposables = CompositeDisposable()

    val user by lazy {
        authRepository.currentUser()
    }

    private var _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    private var _password = MutableLiveData<String>()
    val password: LiveData<String>
        get() = _password

    private var _startLogin = MutableLiveData<Boolean>()
    val startLogin: LiveData<Boolean>
        get() = _startLogin

    private var _startSignUp = MutableLiveData<Boolean>()
    val startSignUp: LiveData<Boolean>
        get() = _startSignUp

    fun login() {
        _startLogin.value = true

        progressListener?.onStarted()
        val disposable = authRepository.login(email.value!!, password.value!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                progressListener?.onSuccess()
            }, {
                progressListener?.onFailure(it.message!!)
            })
        disposables.add(disposable)

        _startSignUp.value = true
    }

    fun setLoginValues(email: String, password: String) {
        _email.value = email
        _password.value = password
    }

    fun register() {
        _startSignUp.value = false
        progressListener?.onStarted()
        val disposable = authRepository.register(email.value!!, password.value!!)
            .andThen(authRepository.login(email.value!!, password.value!!))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                progressListener?.onSuccess()
            }, {
                progressListener?.onFailure(it.message!!)
            })
        disposables.add(disposable)
    }

    fun goToSignUp(view : View) {
        Intent(view.context, SignUpActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

}