/*************************************************
 * Created by Efendi Hariyadi on 02/06/22, 10:02 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 02/06/22, 10:02 PM
 ************************************************/

package com.numesa.android.simpeldesa

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.numesa.android.simpeldesa.databinding.ActivityHomeBinding
import com.numesa.android.simpeldesa.ui.adapters.PostsViewPagerAdapter
import com.numesa.android.simpeldesa.workmanager.FavouritesScheduler
import java.util.concurrent.TimeUnit

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = PostsViewPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
        //TODO Scheduled Work manager For Pushing Favourites on Ssrver
        scheduleFavouriteSyncToServer()


    }

    private fun scheduleFavouriteSyncToServer() {
        val constraints: Constraints = Constraints.Builder().apply {
            setRequiredNetworkType(NetworkType.CONNECTED)
            setRequiresBatteryNotLow(true)
        }.build()

        val request: PeriodicWorkRequest = PeriodicWorkRequest.Builder(
            FavouritesScheduler::class.java, 3, TimeUnit.HOURS, 1, TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .setBackoffCriteria(BackoffPolicy.LINEAR, 1, TimeUnit.HOURS)
            .build()
        val workManager = WorkManager.getInstance(applicationContext)
        workManager.enqueueUniquePeriodicWork(
            "ScheduledFavouriteSyncJob",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }
}