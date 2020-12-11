package com.example.wildriftcommunity.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.wildriftcommunity.R
import com.example.wildriftcommunity.chat.view.ChatFragment
import com.example.wildriftcommunity.notice.NoticeFragment
import com.example.wildriftcommunity.post.view.CreatePostActivity
import com.example.wildriftcommunity.post.view.PostFragment
import com.example.wildriftcommunity.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val postFragment = PostFragment()
        //createPostActivity
        val chatFragment = ChatFragment()
        val noticeFragment = NoticeFragment()
        val profileFragment = ProfileFragment()

        makeCurrentFragment(postFragment)

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