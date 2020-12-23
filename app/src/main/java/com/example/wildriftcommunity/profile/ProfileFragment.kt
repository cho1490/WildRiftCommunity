package com.example.wildriftcommunity.profile

import android.content.Intent
import android.os.Bundle
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
import com.example.wildriftcommunity.databinding.ProfileFragmentBinding
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
                Glide.with(activity!!).load(it.photoUri).into(profileImage)
                nickname.text = it.nickname
                introduce.text = it.introduce
                postCount.text = it.postCount.toString()
                likeCount.text = it.lickCount.toString()
                kindScore.text = it.kindScore.toString()
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

        }

        return binding.root
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