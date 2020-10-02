package com.example.wildriftcommunity

import android.app.Application
import com.example.wildriftcommunity.auth.AuthRepository
import com.example.wildriftcommunity.auth.AuthViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class WildRiftCommunityApplication : Application(), KodeinAware {

    companion object {
        lateinit var instance: WildRiftCommunityApplication private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    override val kodein = Kodein.lazy {
        import(androidXModule(this@WildRiftCommunityApplication))

        bind() from singleton { AuthRepository() }
        bind() from provider { AuthViewModelFactory( instance()) }

    }

}