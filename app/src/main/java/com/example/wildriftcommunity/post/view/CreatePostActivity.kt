package com.example.wildriftcommunity.post.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.wildriftcommunity.ProgressListener
import com.example.wildriftcommunity.R
import com.example.wildriftcommunity.databinding.ActivityCreatePostBinding
import com.example.wildriftcommunity.post.viewmodel.PostViewModel
import com.example.wildriftcommunity.post.viewmodel.PostViewModelFactory
import kotlinx.android.synthetic.main.activity_create_post.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class CreatePostActivity : AppCompatActivity(), ProgressListener, KodeinAware {

    override val kodein by kodein()
    private val factory: PostViewModelFactory by instance()
    private lateinit var postViewModel : PostViewModel
    private lateinit var binding : ActivityCreatePostBinding

    var photoUri : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postViewModel = ViewModelProvider(this, factory).get(PostViewModel::class.java)
        postViewModel.progressListener = this

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_post)
        binding.postViewModel = postViewModel
        binding.lifecycleOwner = this
        var type: String = "Free"

        postViewModel.startCreatePost.observe(this, Observer{
            if (it == true){
                postViewModel.setCreatePostValues(
                    type,
                    binding.postTitle.text.toString(),
                    binding.postBody.text.toString(),
                    photoUri
                )
            }
        })

        postViewModel.startPickImage.observe(this, Observer {
            if (it == true){
                var photoPickerIntent = Intent(Intent.ACTION_PICK)
                photoPickerIntent.type = "image/*"
                startActivityForResult(photoPickerIntent, 0)
            }
        })

        postViewModel.finish.observe(this, Observer {
            if (it == true){
                finish()
            }
        })

        val items = resources.getStringArray(R.array.post_type_array)
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        binding.postTypeSpinner.adapter = spinnerAdapter
        binding.postTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                when(position){
                    0 -> {
                        type = "Free"
                    }
                    1 -> {
                        type = "Duo"
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0){
            if(resultCode == Activity.RESULT_OK ){
                photoUri = data?.data
                binding.image.setImageURI(photoUri)
            } else
                finish()
        }
    }

    override fun onStarted() {
        progressbarCreatePost.visibility = View.VISIBLE
    }

    override fun onSuccess(message: String) {
        progressbarCreatePost.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onFailure(message: String) {
        progressbarCreatePost.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}