package com.prateek.nearwe.application

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.prateek.nearwe.BuildConfig
import com.prateek.nearwe.di.*
import com.prateek.nearwe.utils.DataStoreManager
import io.branch.referral.Branch
import kotlinx.coroutines.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MainApp : Application() {
    lateinit var Latitude: String
    lateinit var Longitude: String
    var isNightModeEnabled = false
    private lateinit var dataStoreManager: DataStoreManager
    val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    companion object {
        lateinit var instance: MainApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        dataStoreManager = DataStoreManager(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidContext(this@MainApp)
            modules(
                listOf(
                    postDatabaseModule,
                    viewModelModule,
                    apiModule,
                    repositoryModule,
                    RetrofitModule,
                    FirestoreModule
                )
            )

        }
        instance = this
        Branch.enableLogging();

        // Branch object initialization
        Branch.getAutoInstance(this);


        applicationScope.launch {
            dataStoreManager.getTheme.collect { counter ->
                if (counter) {

                    AppCompatDelegate
                        .setDefaultNightMode(
                            AppCompatDelegate
                                .MODE_NIGHT_YES
                        );

                } else {

                    AppCompatDelegate
                        .setDefaultNightMode(
                            AppCompatDelegate
                                .MODE_NIGHT_NO
                        );
                }
            }
        }


    }

    override fun onLowMemory() {
        super.onLowMemory()
        applicationScope.cancel()
    }

}