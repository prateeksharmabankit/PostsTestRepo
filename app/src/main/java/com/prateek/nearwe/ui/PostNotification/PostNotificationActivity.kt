/*************************************************
 * Created by Efendi Hariyadi on 03/06/22, 10:45 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 03/06/22, 10:37 AM
 ************************************************/


package com.prateek.nearwe.ui.PostNotification

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.chip.Chip
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.google.firebase.messaging.FirebaseMessaging
import com.prateek.nearwe.R
import com.prateek.nearwe.api.models.Comments.CommentRequest.CommentRequest
import com.prateek.nearwe.api.models.User.UserModel
import com.prateek.nearwe.api.models.posts.PostLikes.PostLikesRequest
import com.prateek.nearwe.api.models.posts.postresponse.Post
import com.prateek.nearwe.application.MainApp
import com.prateek.nearwe.databinding.ActivityCommentsBinding
import com.prateek.nearwe.databinding.ActivityPostnotificationBinding
import com.prateek.nearwe.ui.adapters.CommentsAdapter
import com.prateek.nearwe.ui.comments.CommentsActivity
import com.prateek.nearwe.ui.login.LoginViewModel
import com.prateek.nearwe.ui.posts.PostsViewModel
import com.prateek.nearwe.utils.Utils.CompanionClass.Companion.hideKeyboard
import com.prateek.nearwe.utils.Utils.CompanionClass.Companion.ifNonNull
import com.prateek.nearwe.utils.Utils.CompanionClass.Companion.ifNull
import kotlinx.android.synthetic.main.activity_comments.*
import kotlinx.android.synthetic.main.nav_header_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.Serializable
import java.util.*

class PostNotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostnotificationBinding
    private val postsViewModel: PostsViewModel by viewModel()
    private val loginViewModel: LoginViewModel by viewModel()
    var PostId: Int = 0;
    private lateinit var user: UserModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostnotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            PostId = intent.getIntExtra("postId", 0)

        }





        initObserver()
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener(View.OnClickListener { onBackPressed() })
        // postsViewModel.GetPostLikes(post.postId, UserId)
        loginViewModel.getLoggedInUser();


    }

    fun initObserver() {
        postsViewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
        loginViewModel.userDetails.observe(this, Observer {
            user = it
            it.UserId?.let { userId -> postsViewModel.getPostById(userId, PostId) }
        })
        postsViewModel.AddPostViews(PostId)
        postsViewModel.postList.observe(this) {
            it?.let { list ->
                binding.progressBar.visibility = View.GONE

                setupUI(list.results.data[0])
                binding.cardView.visibility = View.VISIBLE
            }
        }
        postsViewModel.errorMessage.observe(this) {
            Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
        }
    }

    fun setupUI(post: Post) {
        binding.cardView.setOnClickListener {

            val intent = Intent(this, CommentsActivity::class.java)
            intent.putExtra("post", post as Serializable)
            intent.putExtra("UserId", user.UserId)
            intent.putExtra("Name", user.Name)

            startActivity(intent)
        }
        binding.txtTitle.text = post.title
        binding.txtDateTime.text = post.ago
        binding.txtViews.text = post.postViews.toString() + " Views"

        when (post.postType) {
            1 -> binding.imgpostType.setColorFilter(
                ContextCompat.getColor(
                    MainApp.instance,
                    R.color.Green
                ), android.graphics.PorterDuff.Mode.SRC_ATOP
            )
            2 -> binding.imgpostType.setColorFilter(
                ContextCompat.getColor(
                    MainApp.instance,
                    R.color.Orange
                ), android.graphics.PorterDuff.Mode.SRC_ATOP
            )
            3 -> binding.imgpostType.setColorFilter(
                ContextCompat.getColor(
                    MainApp.instance,
                    R.color.Red
                ), android.graphics.PorterDuff.Mode.SRC_ATOP
            )

        }


        val lstValues: List<String> = post.subCategories.split(",").map { it -> it.trim() }
        lstValues.forEach { it ->
            val chip =
                Chip(ContextThemeWrapper(MainApp.instance, R.style.Theme_MaterialComponents_Light))
            chip.text = it
            chip.setChipBackgroundColorResource(R.color.colorPrimary)
            chip.isCloseIconVisible = false
            chip.setTextColor(MainApp.instance.getColor(R.color.primarytext))
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
            )

            layoutParams.setMargins(0, 0, 10, 0)

            binding.lvSubCategory.addView(chip, layoutParams)
        }





        when (post.isAnonymous) {
            0 -> binding.txtName.text = post.users.Name

            1 -> binding.txtName.text = MainApp.instance.getString(R.string.anonymous)


        }

        post.imageUrl.ifNull {

            binding.imgPost.visibility = View.GONE
        }
        post.imageUrl.ifNonNull {


            Glide.with(MainApp.instance)

                .load(post.imageUrl)

                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.imgPost)
            binding.imgPost.visibility = View.VISIBLE
        }

    }
}