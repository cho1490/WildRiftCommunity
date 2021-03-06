package com.example.wildriftcommunity

import android.app.Application
import com.example.wildriftcommunity.auth.AuthViewModelFactory
import com.example.wildriftcommunity.chat.viewmodel.ChatViewModelFactory
import com.example.wildriftcommunity.data.repositories.*
import com.example.wildriftcommunity.notice.viewmodel.NoticeViewModelFactory
import com.example.wildriftcommunity.post.viewmodel.PostViewModelFactory
import com.example.wildriftcommunity.profile.ProfileViewModelFactory
import com.example.wildriftcommunity.util.FirebaseSource
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class WildRiftCommunityApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@WildRiftCommunityApplication))

        bind() from singleton { FirebaseSource() }
        bind() from singleton { AuthRepository( instance()) }
        bind() from singleton { PostRepository( instance()) }
        bind() from singleton { ProfileRepository( instance()) }
        bind() from singleton { ChatRepository( instance()) }
        bind() from singleton { NoticeRepository( instance()) }

        bind() from provider { AuthViewModelFactory( instance()) }
        bind() from provider { PostViewModelFactory( instance()) }
        bind() from provider { ProfileViewModelFactory( instance()) }
        bind() from provider { ChatViewModelFactory( instance()) }
        bind() from provider { NoticeViewModelFactory( instance()) }
    }

}