package com.example.wildriftcommunity.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.wildriftcommunity.ProgressListener
import com.example.wildriftcommunity.R
import com.example.wildriftcommunity.databinding.ActivityProfileEditBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ProfileEditActivity : AppCompatActivity(), ProgressListener, KodeinAware {

    override val kodein by kodein()
    private val factory: ProfileViewModelFactory by instance()
    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding : ActivityProfileEditBinding

    var photoUri : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)
        viewModel.progressListener = this

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_edit)
        binding.profileViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.fetchUserDetails(null)

        viewModel.userDetails.observe(this, Observer {
            binding.apply {
                Glide.with(this.root).load(it.photoUri).into(changeProfileImage)
                currentNickname.text = it.nickname
                currentIntro.text = it.introduce
            }
        })

        viewModel.startUpdate.observe(this, Observer {
            if (it == true){
                    viewModel.setUpdateUserDetails(
                        photoUri,
                        binding.changeNickname.text.toString(),
                        binding.changeIntro.text.toString()
                    )
            }
        })

        viewModel.startPickImage.observe(this, Observer {
            if (it == true) {
                var photoPickerIntent = Intent(Intent.ACTION_PICK)
                photoPickerIntent.type = "image/*"
                startActivityForResult(photoPickerIntent, 0)
            }
        })

        viewModel.startProfile.observe(this, Observer {
            if (it == true){
                finish()
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0){
            if(resultCode == Activity.RESULT_OK ){
                photoUri = data?.data
                binding.changeProfileImage.setImageURI(photoUri)
            } else
                finish()
        }
    }

    override fun onStarted() {
        binding.progressbarProfileEdit.visibility = View.VISIBLE
    }

    override fun onSuccess(message: String) {
        binding.progressbarProfileEdit.visibility = View.GONE
    }

    override fun onFailure(message: String) {
        binding.progressbarProfileEdit.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}