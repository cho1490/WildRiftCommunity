package com.example.wildriftcommunity.auth

import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Completable

class AuthRepository() {

    private val firebase = FirebaseAuth.getInstance()

    fun login(email: String, password: String) =
        Completable.create { emitter ->
            firebase.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (!emitter.isDisposed) {
                    if (it.isSuccessful) {
                        emitter.onComplete()
                    } else
                        emitter.onError(it.exception!!)
                }
            }
        }

    fun register(email: String, password: String) =
        Completable.create { emitter ->
            firebase.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (!emitter.isDisposed) {
                    if (it.isSuccessful) {
                        emitter.onComplete()
                    } else
                        emitter.onError(it.exception!!)
                }
            }
        }

}