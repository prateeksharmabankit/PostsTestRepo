/*************************************************
 * Created by Efendi Hariyadi on 03/06/22, 10:45 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 03/06/22, 10:37 AM
 ************************************************/


package com.prateek.nearwe.ui.updateprofile

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.github.drjacky.imagepicker.ImagePicker
import com.github.drjacky.imagepicker.constant.ImageProvider
import com.google.android.gms.tasks.RuntimeExecutionException
import com.google.android.play.core.review.ReviewException
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.firebase.messaging.FirebaseMessaging
import com.prateek.nearwe.R
import com.prateek.nearwe.api.models.Comments.CommentRequest.CommentRequest
import com.prateek.nearwe.api.models.User.UserModel
import com.prateek.nearwe.api.models.login.profile.UpdateProfileRequest
import com.prateek.nearwe.api.models.posts.AddPost.AddPostRequest
import com.prateek.nearwe.api.models.posts.PostLikes.PostLikesRequest
import com.prateek.nearwe.api.models.posts.postresponse.Post
import com.prateek.nearwe.application.MainApp
import com.prateek.nearwe.databinding.ActivityCommentsBinding
import com.prateek.nearwe.databinding.ActivityUpdateprofileBinding
import com.prateek.nearwe.ui.adapters.CommentsAdapter
import com.prateek.nearwe.ui.login.LoginViewModel
import com.prateek.nearwe.ui.posts.PostsViewModel
import com.prateek.nearwe.utils.Utils
import kotlinx.android.synthetic.main.activity_comments.*
import kotlinx.android.synthetic.main.activity_updateprofile.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.util.*


class UpdateProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateprofileBinding

    private val loginViewModel: LoginViewModel by viewModel()

    private lateinit var file: File


    private lateinit var user: UserModel


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityUpdateprofileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imgUpload.setOnClickListener(View.OnClickListener {
            ImagePicker.with(this)
                .provider(ImageProvider.BOTH).crop(8f, 8f).cropOval()
                .createIntentFromDialog { launcher.launch(it) }
        })
        initObserver()
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        loginViewModel.getLoggedInUser();
        binding.toolbar.setNavigationOnClickListener(View.OnClickListener {

            onBackPressed()

        })
        binding.UpdateNow.setOnClickListener {
            if (binding.etName.text?.isEmpty() == true) {
                binding.etNameLayout.error = "Enter User Name"
            } else {
                Utils.CompanionClass.hideKeyboard(binding.etName,this)
                binding.etNameLayout.error = null
                user.Name = etName.text.toString()
                val updateProfileModel = UpdateProfileRequest()
                updateProfileModel.UserId = user.UserId
                updateProfileModel.image = user.Image
                updateProfileModel.name = user.Name
                loginViewModel.UpdateProfile(updateProfileModel)

            }
        }
    }

    fun initObserver() {
        loginViewModel.userDetails.observe(this, Observer {
            user = it
            Glide.with(MainApp.instance)
                .load(user.Image)
                .circleCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.userImage)
            var name: String? = user.Name
            binding.etName.setText(name)
        })
        loginViewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })


        loginViewModel.imageUploadResponse.observe(this, Observer {
            if (it?.message == "Image Uploaded") {
                user.Image = it.results.data
                Glide.with(MainApp.instance)
                    .load(it.results.data)
                    .circleCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.userImage)

            } else {

            }
        })

        loginViewModel.updateProfileResponse.observe(this, Observer {
            if (it.results.data == "0") {
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                loginViewModel.updateUserProfile(user)
            } else {
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
            }
        })

    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                file = uri.toFile()
                loginViewModel.AddImage(file)

            }
        }
}