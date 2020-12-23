package com.example.wildriftcommunity.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.wildriftcommunity.R
import com.example.wildriftcommunity.chat.view.ChatFragment
import com.example.wildriftcommunity.notice.NoticeFragment
import com.example.wildriftcommunity.post.view.CreatePostActivity
import com.example.wildriftcommunity.post.view.PostFragment
import com.example.wildriftcommunity.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val postFragment = PostFragment()
    //createPostActivity
    private val chatFragment = ChatFragment()
    private val noticeFragment = NoticeFragment()
    private val profileFragment = ProfileFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        makeCurrentFragment(postFragment)

        bottomnavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_post -> makeCurrentFragment(postFragment)
                R.id.menu_create -> startActivity(Intent(this, CreatePostActivity::class.java))
                R.id.menu_chat -> makeCurrentFragment(chatFragment)
                R.id.menu_notice -> makeCurrentFragment(noticeFragment)
                R.id.menu_profile -> {
                    supportFragmentManager.beginTransaction().apply {
                        val bundle: Bundle? = null
                        profileFragment.arguments = bundle
                        replace(R.id.frameLayout, profileFragment)
                        commit()
                    }
                }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 500 && resultCode == RESULT_OK) {
            supportFragmentManager.beginTransaction().apply {
                val destinationUid = data!!.getStringExtra("destinationUid")
                val bundle = Bundle()
                bundle.putString("destinationUid", destinationUid)
                profileFragment.arguments = bundle
                replace(R.id.frameLayout, profileFragment)
                commit()
            }
        }
    }
}