/*************************************************
 * Created by Efendi Hariyadi on 12/06/22, 11:14 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 12/06/22, 11:05 AM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 11/06/22, 1:47 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 11/06/22, 12:08 AM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 02/06/22, 10:02 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 02/06/22, 10:02 PM
 ************************************************/

package com.prateek.nearwe.ui.home

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.prateek.nearwe.databinding.ActivityHomeBinding
import com.prateek.nearwe.ui.adapters.PostsViewPagerAdapter
import com.prateek.nearwe.workmanager.FavouritesScheduler
import java.util.concurrent.TimeUnit
import com.prateek.nearwe.R;

class HomeActivity : AppCompatActivity() {

    //private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_home)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        navController = Navigation.findNavController(this, R.id.activity_main_nav_host_fragment)


        setupWithNavController(bottomNavigationView, navController)

        /* val sectionsPagerAdapter = PostsViewPagerAdapter(this, supportFragmentManager)
         val viewPager: ViewPager = binding.viewPager
         viewPager.adapter = sectionsPagerAdapter
         val tabs: TabLayout = binding.tabs
         tabs.setupWithViewPager(viewPager)
         //TODO Scheduled Work manager For Pushing Favourites on Ssrver
         scheduleFavouriteSyncToServer()*/


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