package com.example.wildriftcommunity.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wildriftcommunity.data.repositories.ProfileRepository

@Suppress("UNCHECKED_CAST")
class ProfileViewModelFactory(private val profileRepository: ProfileRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(profileRepository) as T
    }
}