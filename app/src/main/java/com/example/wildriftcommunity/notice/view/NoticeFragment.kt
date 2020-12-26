package com.example.wildriftcommunity.notice.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.wildriftcommunity.R
import com.example.wildriftcommunity.notice.adapter.NoticeListAdapter
import com.example.wildriftcommunity.notice.viewmodel.NoticeViewModel
import com.example.wildriftcommunity.notice.viewmodel.NoticeViewModelFactory
import kotlinx.android.synthetic.main.notice_fragment.view.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class NoticeFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private lateinit var noticeViewModel: NoticeViewModel
    private val factory: NoticeViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.notice_fragment, container,false)
        noticeViewModel = ViewModelProvider(this, factory).get(NoticeViewModel::class.java)

        view.noticeRecyclerView.apply{
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(false)
            val glideRequestManager = Glide.with(this@NoticeFragment)
            adapter = NoticeListAdapter(noticeViewModel.getCurrentUserUid(), this@NoticeFragment, glideRequestManager)
        }

        return view
    }

}