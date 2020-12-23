package com.example.wildriftcommunity.notice.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wildriftcommunity.data.repositories.NoticeRepository
import com.example.wildriftcommunity.data.repositories.ProfileRepository

@Suppress("UNCHECKED_CAST")
class NoticeViewModelFactory(private val noticeRepository: NoticeRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NoticeViewModel(noticeRepository) as T
    }
}