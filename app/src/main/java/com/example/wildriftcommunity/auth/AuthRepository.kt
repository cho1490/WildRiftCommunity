package com.example.wildriftcommunity.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import java.lang.Exception

class AuthRepository() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseFirestore = FirebaseFirestore.getInstance()

    fun currentUser() = firebaseAuth.currentUser

    fun login(email: String, password: String) =
        Completable.create { emitter ->
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
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
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (!emitter.isDisposed) {
                    if (it.isSuccessful) {
                        emitter.onComplete()
                    } else
                        emitter.onError(it.exception!!)
                }
            }
        }

    fun saveUser(email: String) =
        Completable.create { emitter ->

            val user = User(email)
            val usersRef = firebaseFirestore.collection("users")
            usersRef.document(currentUser()!!.uid)
                .set(user)
                .addOnCompleteListener {
                    if (!emitter.isDisposed) {
                        if (it.isSuccessful) {
                            emitter.onComplete()
                        } else
                            emitter.onError(it.exception!!)
                    }
                }
        }

    fun saveUserDetail(nickname: String) =
        Completable.create { emitter ->

            val user = UserName(nickname)
            val usersRef = firebaseFirestore.collection("userNames")
            usersRef.document(currentUser()!!.uid)
                .set(user)
                .addOnCompleteListener {
                    if (!emitter.isDisposed) {
                        if (!it.isSuccessful) {
                            emitter.onError(it.exception!!)
                        }
                    }
                }

            emitter.onComplete()
        }

    fun checkNickname(nickname: String) =
        Completable.create { emitter ->

        }

}