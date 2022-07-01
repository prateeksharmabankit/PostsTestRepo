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


import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.net.toFile
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.*
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.github.drjacky.imagepicker.ImagePicker
import com.github.drjacky.imagepicker.constant.ImageProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.messaging.FirebaseMessaging
import com.innfinity.permissionflow.lib.requestPermissions
import com.prateek.nearwe.R
import com.prateek.nearwe.api.models.SubCategory.SubCategory
import com.prateek.nearwe.api.models.User.UserModel
import com.prateek.nearwe.api.models.posts.AddPost.AddPostRequest
import com.prateek.nearwe.application.MainApp
import com.prateek.nearwe.databinding.ActivityHomeBinding
import com.prateek.nearwe.ui.adapters.SubCategoryAdapter
import com.prateek.nearwe.ui.login.LoginActivity
import com.prateek.nearwe.ui.login.LoginViewModel
import com.prateek.nearwe.ui.posts.PostsViewModel
import gun0912.tedbottompicker.TedBottomPicker
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val homeViewModel: HomeViewModel by viewModel()
    private val loginViewModel: LoginViewModel by viewModel()
    private val postsViewModel: PostsViewModel by viewModel()
    private lateinit var file: File

    private var postCategoryId: Int = 0
    private lateinit var user: UserModel


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        initObserver()

        setupWithNavController(binding.bottomNavigationView, navController)


        loginViewModel.getAddressHeader(this)
        loginViewModel.getLoggedInUser();


    }


    fun initUI() {
        binding.loginViewModel = loginViewModel
        binding.lifecycleOwner = this
        setSupportActionBar(binding.toolbar)
        navController = Navigation.findNavController(this, R.id.activity_main_nav_host_fragment)
        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, toolbar, R.string.drawer_open,
            R.string.drawer_close
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        appBarConfiguration = AppBarConfiguration(
            navController.graph,
            binding.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
        CoroutineScope(Dispatchers.Main).launch {
            // just call requestPermission and pass in all required permissions
            requestPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ).collect { permissions ->
                // here you get the result of the requests, permissions holds a list of Permission requests and you can check if all of them have been granted:
                val allGranted = permissions.find { !it.isGranted } == null
                // or iterate over the permissions and check them one by one
                permissions.forEach {
                    val granted = it.isGranted
                    // ...
                }
            }


        }
        nav_view.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { menuItem ->
            val id = menuItem.itemId
            if (id == R.id.logout) {

                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder
                    .setTitle("Logout!")
                    .setMessage("Are you sure you want to  logout?")
                    .setPositiveButton(android.R.string.yes,
                        DialogInterface.OnClickListener { dialog, which ->
                            CoroutineScope(Dispatchers.IO).launch {

                                GoogleSignIn.getClient(
                                    applicationContext,
                                    GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
                                ).signOut()
                                postsViewModel.deleteUser()
                                startActivity(
                                    Intent(
                                        applicationContext, LoginActivity
                                        ::class.java
                                    )
                                )
                                finish()

                            }
                        })
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show()


            }

            NavigationUI.onNavDestinationSelected(menuItem, navController)
            val drawer = findViewById<View>(R.id.drawerLayout) as DrawerLayout
            drawer.closeDrawer(GravityCompat.START)
             true
        })


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem) =
        item.onNavDestinationSelected(navController)
                || super.onOptionsItemSelected(item)


    override fun onSupportNavigateUp() = navController.navigateUp(appBarConfiguration)

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    fun openAddBottomSheet(subCategoryList: List<SubCategory>) {

        val bottomSheetDialog = BottomSheetDialog(this, R.style.TransparentDialog)
        bottomSheetDialog.setContentView(R.layout.add_postbottomsheet)
        val tvTitle = bottomSheetDialog.findViewById<TextView>(R.id.tvTitle)
        val PostNow = bottomSheetDialog.findViewById<AppCompatButton>(R.id.PostNow)
        val etTitle = bottomSheetDialog.findViewById<EditText>(R.id.etTitle)

        val checkPostAnonymous = bottomSheetDialog.findViewById<CheckBox>(R.id.checkPostAnonymous)
        val recylerSubCategories =
            bottomSheetDialog.findViewById<RecyclerView>(R.id.recylerSubCategories)

        val radioGroup1 =
            bottomSheetDialog.findViewById<RadioGroup>(R.id.radioGroup1)


        val imgUploadButton = bottomSheetDialog.findViewById<MaterialButton>(R.id.imgUploadButton)
        imgUploadButton?.setOnClickListener(View.OnClickListener {


            ImagePicker.with(this)

                .provider(ImageProvider.BOTH).crop(16f, 9f) //Or bothCameraGallery()
                .createIntentFromDialog { launcher.launch(it) }


        })
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL)
        recylerSubCategories?.layoutManager = staggeredGridLayoutManager
        val adapter = SubCategoryAdapter(subCategoryList)
        recylerSubCategories?.adapter = adapter
        val postModel = AddPostRequest()
        postModel.PostType = 1
        when (postCategoryId) {
            941590 -> tvTitle?.text = getString(R.string.fabtextone)
            487951 -> tvTitle?.text = getString(R.string.fabtexttwo)
            123251 -> {
                tvTitle?.text = getString(R.string.fabtextthree)
                imgUploadButton?.visibility = View.VISIBLE
            }
            else -> println("I don't know anything about it")
        }
        radioGroup1?.setOnCheckedChangeListener { _, checkedId ->
            val radioButton = bottomSheetDialog.findViewById<RadioButton>(checkedId)
            if (radioButton != null) {
                when (radioButton.id) {
                    R.id.postRadioOne -> postModel.PostType = 1
                    R.id.postRadioTwo -> postModel.PostType = 2
                    R.id.postRadioThree -> postModel.PostType = 3
                }
            }
        }

        PostNow?.setOnClickListener(View.OnClickListener {
            postModel.CategoryId = postCategoryId
            when (postCategoryId) {
                123251 -> {

                    postModel.Latitude = MainApp.instance.Latitude
                    postModel.Longitude = MainApp.instance.Longitude


                    postModel.CategoryName = "What is this thing?"

                    postModel.IsAnonymous = if (checkPostAnonymous!!.isChecked) 1 else 0
                    postModel.UserId = user.UserId
                    postModel.Title = etTitle?.text.toString().trim()
                    val selectedEngineers: List<SubCategory> = subCategoryList
                        .filterIndexed { index, engineer ->
                            engineer.isCHecked


                        }


                    postModel.SubCategories =
                        selectedEngineers.joinToString { it.subCategoryName.toString()!! }
                            .split(",")
                            .toString().drop(1)
                            .dropLast(1)

                    if (postModel.SubCategories!!.isEmpty()) {

                        Toast.makeText(applicationContext, "Please Select Tags", Toast.LENGTH_SHORT)
                            .show()
                    } else if (etTitle != null) {
                        if (etTitle.text.isEmpty()) {
                            Toast.makeText(
                                applicationContext,
                                "Please Add Post Description",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else if (file == null) {
                            Toast.makeText(
                                applicationContext,
                                "Please Upload Image",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else {
                            homeViewModel.AddWhatsIsPost(file, postModel)
                            bottomSheetDialog.dismiss()
                        }


                    }


                }
                else -> {


                    when (postCategoryId) {


                        487951 -> {
                            postModel.CategoryName = "Hobbies & Interests"
                        }

                        941590 -> {
                            postModel.CategoryName = "Near by"
                        }
                    }


                    postModel.Latitude = MainApp.instance.Latitude
                    postModel.Longitude = MainApp.instance.Longitude
                    postModel.imageUrl = null
                    postModel.IsAnonymous = if (checkPostAnonymous!!.isChecked) 1 else 0
                    postModel.UserId = user.UserId
                    postModel.Title = etTitle?.text.toString().trim()
                    val selectedEngineers: List<SubCategory> = subCategoryList
                        .filterIndexed { index, engineer -> engineer.isCHecked }
                    postModel.SubCategories =
                        selectedEngineers.joinToString { it.subCategoryName.toString()!! }
                            .split(",")
                            .toString().drop(1)
                            .dropLast(1)
                    if (postModel.SubCategories!!.isEmpty()) {

                        Toast.makeText(applicationContext, "Please Select Tags", Toast.LENGTH_SHORT)
                            .show()
                    } else if (etTitle != null) {
                        if (etTitle.text.isEmpty()) {
                            Toast.makeText(
                                applicationContext,
                                "Please Add Post Description",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else {
                            homeViewModel.AddPost(postModel)
                            bottomSheetDialog.dismiss()
                        }


                    }

                }

            }


        })


        bottomSheetDialog.show()

    }

    fun add_Interest_Hobiles_post_click(view: View) {
        postCategoryId = 487951
        homeViewModel.getSubcategoriesByCategoryId(postCategoryId)

    }

    fun add_whats_new_click(view: View) {
        postCategoryId = 123251
        homeViewModel.getSubcategoriesByCategoryId(postCategoryId)

    }

    fun add_new_post_click(view: View) {
        postCategoryId = 941590
        homeViewModel.getSubcategoriesByCategoryId(postCategoryId)
    }

    fun initObserver() {
        homeViewModel.userList.observe(this, Observer {
            openAddBottomSheet(it.results.data)
        })

        loginViewModel.userDetails.observe(this, Observer {
            user = it
            FirebaseMessaging.getInstance().token.addOnCompleteListener {
                if (it.isComplete) {

                    homeViewModel.updateToken(user.UserId, it.result.toString())

                }
            }
            txtNavName.text = user.Name

        })
        homeViewModel.addPostResponse.observe(this, Observer {
            Toast.makeText(applicationContext, "Post Added Successfully", Toast.LENGTH_SHORT).show()


        })

        homeViewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
    }
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val uri = it.data?.data!!

            file = uri.toFile()
        }
    }



}