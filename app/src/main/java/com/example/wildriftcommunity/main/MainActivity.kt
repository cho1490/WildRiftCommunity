package com.example.wildriftcommunity.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.wildriftcommunity.R
import com.example.wildriftcommunity.auth.AuthViewModel
import com.example.wildriftcommunity.auth.AuthViewModelFactory
import com.example.wildriftcommunity.chat.ChatFragment
import com.example.wildriftcommunity.databinding.ActivityLoginBinding
import com.example.wildriftcommunity.databinding.ActivityMainBinding
import com.example.wildriftcommunity.notice.NoticeFragment
import com.example.wildriftcommunity.post.CreatePostActivity
import com.example.wildriftcommunity.post.PostFragment
import com.example.wildriftcommunity.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val postFragment = PostFragment()
        //createPostActivity
        val chatFragment = ChatFragment()
        val noticeFragment = NoticeFragment()
        val profileFragment = ProfileFragment()

        bottomnavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_post -> makeCurrentFragment(postFragment)
                R.id.menu_create -> startActivity(Intent(this, CreatePostActivity::class.java))
                R.id.menu_chat -> makeCurrentFragment(chatFragment)
                R.id.menu_notice -> makeCurrentFragment(noticeFragment)
                R.id.menu_profile -> makeCurrentFragment(profileFragment)
            }
            true
        }

    }

    private fun makeCurrentFragment(fragment : Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, fragment)
            commit()
        }
    }



}