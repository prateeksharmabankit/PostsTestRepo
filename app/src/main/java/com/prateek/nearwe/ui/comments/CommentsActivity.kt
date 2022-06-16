/*************************************************
 * Created by Efendi Hariyadi on 03/06/22, 10:45 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 03/06/22, 10:37 AM
 ************************************************/


package com.prateek.nearwe.ui.comments

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.birjuvachhani.locus.Locus
import com.prateek.nearwe.R
import com.prateek.nearwe.api.models.Comments.CommentRequest.CommentRequest
import com.prateek.nearwe.api.models.User.UserModel
import com.prateek.nearwe.api.models.posts.PostLikes.PostLikesRequest
import com.prateek.nearwe.api.models.posts.Result
import com.prateek.nearwe.databinding.ActivityCommentsBinding
import com.prateek.nearwe.ui.adapters.CommentsAdapter
import com.prateek.nearwe.ui.login.LoginViewModel
import com.prateek.nearwe.ui.posts.PostsViewModel
import com.prateek.nearwe.utils.Utils.CompanionClass.Companion.hideKeyboard
import kotlinx.android.synthetic.main.activity_comments.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class CommentsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCommentsBinding
    private val commentsViewModel: CommentsViewModel by viewModel()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val postsViewModel: PostsViewModel by viewModel()
    private val userViewModel: LoginViewModel by viewModel()
    private lateinit var user: UserModel
    private lateinit var post: Result
    var latitude: String = ""
    var longitude: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCommentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        post = intent.extras!!.get("post") as Result

        val addressDetails = intent.extras!!.get("addressDetails")
        binding.txtName.text = post.Name
        binding.txtTitle.text = post.Title
        binding.txtDateTime.text = post.Ago
        binding.txtViews.text = post.PostViews.toString()
        binding.txtLocation.text = addressDetails.toString()
        binding.txtLike.setOnClickListener(View.OnClickListener {
            var LikeUnlikeRequest = PostLikesRequest()
            LikeUnlikeRequest.UserId = user.UserId
            LikeUnlikeRequest.PostId = post.PostId
            postsViewModel.LikeUnlikePost(LikeUnlikeRequest)
            if (post.IsLiked == 0) {
                binding.txtLike.setTextColor(resources.getColor(R.color.Red))
                post.IsLiked = 1;

            } else {
                binding.txtLike.setTextColor(resources.getColor(R.color.Gray))
                post.IsLiked = 0;
            }
        })
        if (post.IsLiked == 0) {
            binding.txtLike.setTextColor(resources.getColor(R.color.Gray))

        } else {
            binding.txtLike.setTextColor(resources.getColor(R.color.Red))
        }
        linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.txtSendMessage.setOnClickListener(View.OnClickListener {
            if (binding.etMessage.text.isNullOrBlank()) {
                return@OnClickListener
            } else {

                val scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                    txtSendMessage,
                    PropertyValuesHolder.ofFloat("scaleX", 0.5f),
                    PropertyValuesHolder.ofFloat("scaleY", 0.5f)
                )
                scaleDown.duration = 200
                scaleDown.repeatMode = ValueAnimator.REVERSE
                scaleDown.repeatCount = 1
                scaleDown.start()
                scaleDown.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animator: Animator) {}
                    override fun onAnimationEnd(animator: Animator) {

                        hideKeyboard(etMessage, context = applicationContext)
                        var commentRequest = CommentRequest()
                        commentRequest.CommentContent = binding.etMessage.text!!.trim().toString()
                        commentRequest.UserId = user.UserId
                        commentRequest.PostId = post.PostId
                        commentRequest.UserName = user.Name;
                        commentRequest.DateTime = System.currentTimeMillis()
                        if (post.UserId == user.UserId.toString()) {
                            commentRequest.IsOwner = 1
                        } else {
                            commentRequest.IsOwner = 1
                        }

                        commentsViewModel.addPostGroup(commentRequest)
                        etMessage.text!!.clear()

                    }

                    override fun onAnimationCancel(animator: Animator) {}
                    override fun onAnimationRepeat(animator: Animator) {}
                })


            }

        })


        initObserver()
        // commentsViewModel.getAllComments(id)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener(View.OnClickListener { onBackPressed() })
        Locus.getCurrentLocation(applicationContext) { result ->
            result.location?.let {

                latitude = it.latitude.toString()
                longitude = it.longitude.toString()


            }
            result.error?.let {

                Locus.getCurrentLocation(applicationContext) { result ->
                    result.location?.let {
                        latitude = it.latitude.toString()
                        longitude = it.longitude.toString()

                    }
                    result.error?.let { /* Received error! */ }
                }
            }
        }
    }

    fun initObserver() {
        userViewModel.getLoggedInUser()
        /*commentsViewModel.commentsList.observe(this) {
            val adapter = CommentsAdapter(it)
            binding.recyclerView.adapter = adapter
        }*/

        /* commentsViewModel.errorMessage.observe(this) {
             Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
         }

         commentsViewModel.loading.observe(this, Observer {
             if (it) {
                 binding.progressBar.visibility = View.VISIBLE
             } else {
                 binding.progressBar.visibility = View.GONE
             }
         })*/
        userViewModel.userDetails.observe(this, Observer {
            user = it
            postsViewModel.AddPostViews(post.PostId)
        })
        postsViewModel.addPostLikesResponse.observe(this, Observer {


            postsViewModel.getAllPosts(user.UserId, latitude, longitude)
        })

        commentsViewModel.commentsModelList.observe(this, Observer {

            val adapter = CommentsAdapter(it)
            binding.recyclerView.adapter = adapter
        })
        commentsViewModel.getSavedAddresses(post.PostId)


    }
}