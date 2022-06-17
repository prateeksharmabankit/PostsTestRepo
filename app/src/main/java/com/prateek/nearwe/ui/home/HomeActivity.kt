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
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.birjuvachhani.locus.Locus
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView
import com.prateek.nearwe.R
import com.prateek.nearwe.api.models.SubCategory.Result
import com.prateek.nearwe.api.models.User.UserModel
import com.prateek.nearwe.api.models.posts.AddPost.AddPostRequest
import com.prateek.nearwe.ui.adapters.SubCategoryAdapter
import com.prateek.nearwe.ui.login.LoginViewModel
import com.prateek.nearwe.ui.posts.PostsViewModel
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeActivity : AppCompatActivity() {

    //private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val homeViewModel: HomeViewModel by viewModel()
    private val postsViewModel: PostsViewModel by viewModel()
    private val userViewModel: LoginViewModel by viewModel()

    private var postCategoryId: Int = 0
    private lateinit var user: UserModel
    var latitude: String = ""
    var longitude: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_home)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        navController = Navigation.findNavController(this, R.id.activity_main_nav_host_fragment)


        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.drawer_open,
            R.string.drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        val navView: NavigationView = findViewById(R.id.nav_view)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_to_home, R.id.nav_to_home, R.id.nav_to_home
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        initObserver()
        Locus.getCurrentLocation(applicationContext) { result ->
            result.location?.let {

                latitude = it.latitude.toString()
                longitude = it.longitude.toString()
                userViewModel.getLoggedInUser()

            }
            result.error?.let {


            }
        }








        setupWithNavController(bottomNavigationView, navController)


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {

        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    fun openAddBottomSheet(subCategoryList: List<Result>) {
        val bottomSheetDialog = BottomSheetDialog(this, R.style.TransparentDialog)
        bottomSheetDialog.setContentView(R.layout.add_postbottomsheet)
        val tvTitle = bottomSheetDialog.findViewById<TextView>(R.id.tvTitle)
        val PostNow = bottomSheetDialog.findViewById<AppCompatButton>(R.id.PostNow)

        val checkPostAnonymous = bottomSheetDialog.findViewById<CheckBox>(R.id.checkPostAnonymous)
        val recylerSubCategories =
            bottomSheetDialog.findViewById<RecyclerView>(R.id.recylerSubCategories)

        val radioGroup1 =
            bottomSheetDialog.findViewById<RadioGroup>(R.id.radioGroup1)


        val radioSelected = radioGroup1?.checkedRadioButtonId


        val staggeredGridLayoutManager = StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL)
        recylerSubCategories?.layoutManager = staggeredGridLayoutManager
        val adapter = SubCategoryAdapter(subCategoryList)
        recylerSubCategories?.adapter = adapter
        when (postCategoryId) {
            1 -> tvTitle?.text = "Create NearBy Post"
            2 -> tvTitle?.text = "Create Interest and Hobby Based Post"
            3 -> tvTitle?.text = "Ask for the identification of mysterious objects"
            else -> println("I don't know anything about it")
        }

        PostNow?.setOnClickListener(View.OnClickListener {
            var postModel = AddPostRequest()
            postModel.Latitude = latitude
            postModel.Longitude = longitude
            when (radioSelected) {
                R.id.postRadioOne -> postModel.PostType = 1
                R.id.postRadioTwo -> postModel.PostType = 2
                R.id.postRadioThree -> postModel.PostType = 3
            }
            postModel.IsAnonymous = if (checkPostAnonymous!!.isChecked) 1 else 0
            postModel.UserId = user.UserId
            postModel.Title = tvTitle?.text.toString().trim()
            val selectedEngineers: List<Result> = subCategoryList
                .filterIndexed { index, engineer ->
                    engineer.isCHecked


                }


            postModel.PostSubCategories=selectedEngineers.joinToString { it.Key!! }.split(",").toString().drop(1).dropLast(1)



              postsViewModel.AddPost(postModel)
            bottomSheetDialog.dismiss()
        })


        bottomSheetDialog.show()

    }

    fun add_Interest_Hobiles_post_click(view: View) {
        postCategoryId = 2
        homeViewModel.getSubcategoriesByCategoryId(postCategoryId)

    }

    fun add_whats_new_click(view: View) {
        postCategoryId = 4
        homeViewModel.getSubcategoriesByCategoryId(postCategoryId)

    }

    fun add_new_post_click(view: View) {
        postCategoryId = 1
        homeViewModel.getSubcategoriesByCategoryId(postCategoryId)
    }

    fun initObserver() {
        homeViewModel.userList.observe(this, Observer {
            openAddBottomSheet(it.Result)
        })

        userViewModel.userDetails.observe(this, Observer {
            user = it

        })
        postsViewModel.addPostResponse.observe(this, Observer {
            Toast.makeText(applicationContext,"Post Added Successfully",Toast.LENGTH_SHORT).show()

        })
    }
}