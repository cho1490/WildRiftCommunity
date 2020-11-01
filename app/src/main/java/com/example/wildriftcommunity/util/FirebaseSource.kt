package com.example.wildriftcommunity.util

import android.net.Uri
import com.example.wildriftcommunity.data.models.Post
import com.example.wildriftcommunity.data.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import io.reactivex.Completable
import java.text.SimpleDateFormat
import java.util.*

class FirebaseSource {

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private val storage: StorageReference by lazy { FirebaseStorage.getInstance().reference }

    lateinit var userDetails: User
    lateinit var postList: List<Post>

    fun currentUser() = auth.currentUser

    fun login(email: String, password: String) =
        Completable.create { emitter ->
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
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
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (!emitter.isDisposed) {
                    if (it.isSuccessful) {
                        emitter.onComplete()
                    } else
                        emitter.onError(it.exception!!)
                }
            }
        }

    fun createUser(email: String) =
        Completable.create { emitter ->
            val user = User(email)
            val usersRef = db.collection("users")
            usersRef.document(auth.currentUser!!.uid)
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

    fun fetchUserDetails() =
        Completable.create { emitter ->
            val usersRef = db.collection("users")
            usersRef.document(currentUser()!!.uid).get()
                .addOnCompleteListener {
                    if (!emitter.isDisposed) {
                        if (it.isSuccessful) {
                            userDetails = it.result?.toObject(User::class.java)!!
                        }
                        emitter.onComplete()
                    } else
                        emitter.onError(it.exception!!)
                }
        }

    fun createPost(title: String, body: String, photoUri: Uri?) =
        Completable.create { emitter ->
            val user = db.collection("users").document(currentUser()!!.uid).get().result?.toObject(User::class.java)
            println("csh : " + user!!.email + " - " + user!!.photoUri)
            val userRef = db.collection("posts")
            if (photoUri != null) {
                val timeStamp = SimpleDateFormat("yyyy-mm-dd_HH:mm:ss").format(Date())
                val imageFileName = "Image_" + timeStamp + "_.png"

                val storageRef = storage.child("images/").child(imageFileName)
                storageRef.putFile(photoUri!!).continueWithTask {
                    return@continueWithTask storageRef.downloadUrl
                }.addOnSuccessListener { uri ->
                    val post = Post("Free", title, body, uri.toString(), System.currentTimeMillis(), user)
                    userRef.document().set(post)
                        .addOnCompleteListener {
                            if (!emitter.isDisposed) {
                                if (it.isSuccessful) {
                                    emitter.onComplete()
                                } else
                                    emitter.onError(it.exception!!)
                            }
                        }
                }
            } else {
                val post = Post(title, body, "")
                userRef.document().set(post)
                    .addOnCompleteListener {
                        if (!emitter.isDisposed) {
                            if (it.isSuccessful) {
                                emitter.onComplete()
                            } else
                                emitter.onError(it.exception!!)
                        }
                    }
            }
        }

}