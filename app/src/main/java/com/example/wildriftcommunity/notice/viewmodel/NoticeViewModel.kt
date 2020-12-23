package com.example.wildriftcommunity.notice.viewmodel

import androidx.lifecycle.ViewModel
import com.example.wildriftcommunity.ProgressListener
import com.example.wildriftcommunity.data.repositories.NoticeRepository

class NoticeViewModel(private val noticeRepository: NoticeRepository) : ViewModel() {

    fun getCurrentUserUid() = noticeRepository.getCurrentUserUid()

}