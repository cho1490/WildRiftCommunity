package com.example.wildriftcommunity.auth

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
import com.example.wildriftcommunity.ProgressListener
import com.example.wildriftcommunity.R
import com.example.wildriftcommunity.databinding.ActivitySetUpBinding
import com.example.wildriftcommunity.main.MainActivity
import kotlinx.android.synthetic.main.activity_set_up.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SetUpActivity : AppCompatActivity(), ProgressListener, KodeinAware {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()
    private lateinit var viewModel : AuthViewModel
    private lateinit var binding : ActivitySetUpBinding

    var photoUri : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        viewModel.progressListener = this

        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_up)
        binding.authViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.startUpdate.observe(this, Observer {
            if (it == true){
                viewModel.setUpdateUserDetails(
                    photoUri,
                    binding.editTextNickname.text.toString(),
                    binding.editTextIntroduce.text.toString()
                )
            }
        })

        viewModel.startPickImage.observe(this, Observer {
            if (it == true) {
                val photoPickerIntent = Intent(Intent.ACTION_PICK)
                photoPickerIntent.type = "image/*"
                startActivityForResult(photoPickerIntent, 0)
            }
        })

        viewModel.startMain.observe(this, Observer {
            if (it == true) {
                startActivity(Intent(this, MainActivity::class.java))
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0){
            if(resultCode == Activity.RESULT_OK ){
                photoUri = data?.data
                binding.initProfileImage.setImageURI(photoUri)
            } else
                finish()
        }

    }

    override fun onStarted() {
        progressbarSetUp.visibility = View.VISIBLE
    }

    override fun onSuccess(message: String) {
        progressbarSetUp.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onFailure(message: String) {
        progressbarSetUp.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}