package com.example.wildriftcommunity.util

import android.net.Uri
import com.example.wildriftcommunity.data.models.Post
import com.example.wildriftcommunity.data.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import io.reactivex.Completable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class FirebaseSource {

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private val storage: StorageReference by lazy { FirebaseStorage.getInstance().reference }
    private val realtimeDb: DatabaseReference by lazy { FirebaseDatabase.getInstance().reference }

    lateinit var userDetails: User
    var postList = ArrayList<Post>()
    var postInUserList = ArrayList<User>()

    fun currentUser() = auth.currentUser

    fun login(email: String, password: String) =
        Completable.create { emitter ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
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
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
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
            usersRef.document(currentUser()!!.uid).set(user)
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

    fun updateUserDetails(photoUri: Uri?, nickname: String, introduce: String) =
        Completable.create { emitter ->
            val userRef = db.collection("users")
            var fieldUpdateMap = mutableMapOf<String, Any>()
            if (photoUri != null) {
                val timeStamp = SimpleDateFormat("yyyy-mm-dd_HH:mm:ss").format(Date())
                val imageFileName = "Image_" + timeStamp + "_.png"
                val storageRef = storage.child("images/").child(imageFileName)
                storageRef.putFile(photoUri).continueWithTask {
                    return@continueWithTask storageRef.downloadUrl
                }.addOnSuccessListener { uri ->
                    fieldUpdateMap["photoUri"] = uri
                }
            }
            if (nickname != ""){
                fieldUpdateMap["nickname"] = nickname
            }

            if (introduce != ""){
                fieldUpdateMap["introduce"] = introduce
            }

            userRef.document(currentUser()!!.uid).update(fieldUpdateMap)
                .addOnCompleteListener {
                    if (!emitter.isDisposed) {
                        if (it.isSuccessful) {
                            emitter.onComplete()
                        } else
                            emitter.onError(it.exception!!)
                    }
                }
        }

    fun createPost(type: String, title: String, body: String, photoUri: Uri?) =
        Completable.create { emitter ->
            val postRef = db.collection("posts")
            if (photoUri != null) {
                val timeStamp = SimpleDateFormat("yyyy-mm-dd_HH:mm:ss").format(Date())
                val imageFileName = "Image_" + timeStamp + "_.png"
                val storageRef = storage.child("images/").child(imageFileName)

                storageRef.putFile(photoUri).continueWithTask {
                    return@continueWithTask storageRef.downloadUrl
                }.addOnSuccessListener { uri ->
                    val post = Post(type, title, body, uri.toString(), System.currentTimeMillis(), currentUser()!!.uid)
                    postRef.document().set(post)
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
                val post = Post(type, title, body, "", System.currentTimeMillis(), currentUser()!!.uid)
                postRef.document().set(post)
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

    fun setPostList(type: String) =
        Completable.create { emitter ->
            val postsRef = db.collection("posts")
            postsRef.whereEqualTo("type", type).orderBy("timestamp", Query.Direction.ASCENDING).get()
                .addOnCompleteListener {
                    if (!emitter.isDisposed) {
                        if (it.isSuccessful) {
                            postList.clear()
                            for (snapshot in it.result!!) {
                                val postItem = snapshot.toObject(Post::class.java)
                                postList.add(postItem)
                                val userItem = db.collection("users")
                                    .document(postItem.userUid!!)
                                    .get().result!!.toObject(User::class.java)
                                postInUserList.add(userItem!!)
                            }
                            emitter.onComplete()
                        } else
                            emitter.onError(it.exception!!)
                    }
                }
        }

    fun sendMessage(user: User, message: String) {
        realtimeDb.child("users").child(currentUser()!!.uid)
    }

}