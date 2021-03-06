package com.example.wildriftcommunity.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.wildriftcommunity.ProgressListener
import com.example.wildriftcommunity.R
import com.example.wildriftcommunity.chat.view.ChatFragment
import com.example.wildriftcommunity.data.models.User
import com.example.wildriftcommunity.databinding.ProfileFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class ProfileFragment : Fragment(), KodeinAware, ProgressListener {

    override val kodein by kodein()
    private val factory: ProfileViewModelFactory by instance()
    private lateinit var profileViewModel: ProfileViewModel
    lateinit var binding: ProfileFragmentBinding
    var destinationUid: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.profile_fragment, container, false)
        binding.lifecycleOwner = activity
        binding.profileViewModel = profileViewModel
        profileViewModel.progressListener = this

        val bundle = arguments
        destinationUid = if (bundle == null){
            setVisibilityMy()
            null
        } else {
            setVisibilityOther()
            bundle.getString("destinationUid", "")
        }

        profileViewModel.userDetails.observe(viewLifecycleOwner, Observer {
            binding.apply {
                if(it.photoUri != "")
                    Glide.with(activity!!).load(it.photoUri).into(profileImage)
                nickname.text = it.nickname
                introduce.text = it.introduce
                postCount.text = it.postCount.toString()
                likeCount.text = it.likeCount.toString()
                kindScore.text = it.kindScore.toString()
                if(it.favorites.containsKey(profileViewModel!!.getCurrentUserUid()!!.uid)) {
                    // like status
                    thumbsUp.setBackgroundResource(R.drawable.ic_baseline_thumb_up_24)
                }else{
                    // unlike status
                    thumbsUp.setBackgroundResource(R.drawable.ic_baseline_thumb_down_24 )
                }
            }
        })

        profileViewModel.startProfileEdit.observe(viewLifecycleOwner, Observer {
            if(it == true)
                startActivity(Intent(activity, ProfileEditActivity::class.java))
        })

        binding.goChat.setOnClickListener {
            val fragment: Fragment = ChatFragment()
            val fragmentManager: FragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            val args = Bundle()
            args.putString("destinationUid", destinationUid)
            fragment.arguments = args
            fragmentTransaction.replace(R.id.frameLayout, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        binding.thumbsUp.setOnClickListener {
            favoriteEvent()
            //profileViewModel.thumbsUpClick(destinationUid!!)
        }

        return binding.root
    }

    private fun favoriteEvent() {
        val fireStore = FirebaseFirestore.getInstance()
        val tsDoc = fireStore.collection("users").document(destinationUid!!)
        fireStore.runTransaction { transaction ->
            val uid = profileViewModel.getCurrentUserUid()!!.uid
            var userDTO = transaction.get(tsDoc).toObject(User::class.java)

            if(userDTO!!.favorites.containsKey(uid)){
                userDTO.likeCount = userDTO.likeCount - 1
                userDTO.favorites.remove(uid)
                binding.likeCount.text = userDTO.likeCount.toString()
                binding.thumbsUp.setBackgroundResource(R.drawable.ic_baseline_thumb_down_24)
            }else{
                userDTO.likeCount = userDTO.likeCount + 1
                userDTO.favorites[uid] = true
                binding.likeCount.text = userDTO.likeCount.toString()
                binding.thumbsUp.setBackgroundResource(R.drawable.ic_baseline_thumb_up_24)
            }

            transaction.set(tsDoc, userDTO)
        }
    }

    override fun onStart() {
        super.onStart()
        if (destinationUid == null)
            profileViewModel.fetchUserDetails(null)
        else {
            profileViewModel.fetchUserDetails(destinationUid)
        }
    }

    override fun onStarted() {
        binding.progressbarProfile.visibility = View.VISIBLE
    }

    override fun onSuccess(message: String) {
        binding.progressbarProfile.visibility = View.GONE
    }

    override fun onFailure(message: String) {
        binding.progressbarProfile.visibility = View.GONE
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    private fun setVisibilityMy(){
        binding.modify.visibility = View.VISIBLE
        binding.goChat.visibility = View.GONE
        binding.thumbsUp.visibility = View.GONE
    }

    private fun setVisibilityOther(){
        binding.modify.visibility = View.GONE
        binding.goChat.visibility = View.VISIBLE
        binding.thumbsUp.visibility = View.VISIBLE
    }

}