
package com.prateek.nearwe.application

import android.app.Application
import com.prateek.nearwe.BuildConfig

import com.prateek.nearwe.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MainApp : Application() {
    companion object {
        lateinit var instance: MainApp
            private set
    }
    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidContext(this@MainApp)
            modules(listOf(
                postDatabaseModule,
                viewModelModule,
                apiModule,
                repositoryModule,
                RetrofitModule,
                FirestoreModule
            ))

        }
        instance = this
    }
}