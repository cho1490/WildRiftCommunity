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

    private var _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    private var _password = MutableLiveData<String>()
    val password: LiveData<String>
        get() = _password


    fun login() {
        if (email.value.isNullOrEmpty() || password.value.isNullOrEmpty()) {
            progressListener?.onFailure("Invalid email or password")
            return
        }

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