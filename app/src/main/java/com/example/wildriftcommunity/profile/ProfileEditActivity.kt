package com.example.wildriftcommunity.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.wildriftcommunity.ProgressListener
import com.example.wildriftcommunity.R
import com.example.wildriftcommunity.databinding.ActivityProfileEditBinding
import kotlinx.android.synthetic.main.activity_profile_edit.*
import kotlinx.android.synthetic.main.profile_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ProfileEditActivity : AppCompatActivity(), ProgressListener, KodeinAware {

    override val kodein by kodein()
    private val factory: ProfileViewModelFactory by instance()
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var binding : ActivityProfileEditBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)

        profileViewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)
        profileViewModel.progressListener = this

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_edit)
        binding.profileViewModel = profileViewModel
        binding.lifecycleOwner = this

        profileViewModel.userDetails.observe(this, Observer {
            binding.apply {
                Glide.with(this.root).load(it.photoUri).into(profileImage)
                currentNickname.text = it.nickname
                currentIntro.text = it.introduce
            }
        })
    }

    override fun onStart() {
        super.onStart()
        profileViewModel.fetchUserDetail()
    }

    override fun onStarted() {
        progressbarProfileEdit.visibility = View.VISIBLE
    }

    override fun onSuccess(message: String) {
        progressbarProfileEdit.visibility = View.GONE
    }

    override fun onFailure(message: String) {
        progressbarProfileEdit.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}