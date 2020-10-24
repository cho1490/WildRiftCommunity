package com.example.wildriftcommunity.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.wildriftcommunity.ProgressListener
import com.example.wildriftcommunity.R
import com.example.wildriftcommunity.databinding.ProfileFragmentBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.profile_fragment.*
import org.kodein.di.android.x.kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class ProfileFragment : Fragment(), KodeinAware, ProgressListener {

    override val kodein by kodein()
    private val factory: ProfileViewModelFactory by instance()
    private lateinit var profileViewModel: ProfileViewModel
    lateinit var binding: ProfileFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.profile_fragment, container, false)
        binding.lifecycleOwner = activity
        binding.profileViewModel = profileViewModel
        profileViewModel.progressListener = this

        profileViewModel.userDetails.observe(viewLifecycleOwner, Observer {
            binding.nickname.text = it.nickname
            binding.introduce.text = it.introduce
            binding.postCountText.text = it.postCount.toString()
            binding.likeCountText.text = it.lickCount.toString()
            binding.kindScoreText.text = it.kindScore.toString()
        })

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        profileViewModel.fetchUserDetail()
    }

    override fun onStarted() {
        progressbarProfile.visibility = View.VISIBLE
    }

    override fun onSuccess(message: String) {
        progressbarProfile.visibility = View.GONE
    }

    override fun onFailure(message: String) {
        progressbarProfile.visibility = View.GONE
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

}