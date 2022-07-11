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
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.drjacky.imagepicker.ImagePicker
import com.github.drjacky.imagepicker.constant.ImageProvider
import com.google.android.gms.tasks.RuntimeExecutionException
import com.google.android.play.core.review.ReviewException
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.model.ReviewErrorCode
import com.google.firebase.messaging.FirebaseMessaging
import com.prateek.nearwe.R
import com.prateek.nearwe.api.models.Comments.CommentRequest.CommentRequest
import com.prateek.nearwe.api.models.User.UserModel
import com.prateek.nearwe.api.models.chatrooms.AddchatRoom.AddChatroomRequest
import com.prateek.nearwe.api.models.posts.PostLikes.PostLikesRequest
import com.prateek.nearwe.api.models.posts.postresponse.Post
import com.prateek.nearwe.databinding.ActivityCommentsBinding
import com.prateek.nearwe.ui.adapters.CommentsAdapter
import com.prateek.nearwe.ui.chatrooms.ChatroomViewModel
import com.prateek.nearwe.ui.directchat.DirectChatActivity
import com.prateek.nearwe.ui.login.LoginViewModel
import com.prateek.nearwe.ui.posts.PostsViewModel
import kotlinx.android.synthetic.main.activity_comments.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.Serializable
import java.util.*


class CommentsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCommentsBinding
    private val commentsViewModel: CommentsViewModel by viewModel()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val postsViewModel: PostsViewModel by viewModel()
    private val loginViewModel: LoginViewModel by viewModel()
    private lateinit var post: Post
    private lateinit var file: File
    var Name: String = ""
    private val commentList = ArrayList<CommentRequest>()
    private lateinit var adapter: CommentsAdapter
    private lateinit var user: UserModel
   /* private lateinit var reviewInfo: ReviewInfo*/
    private val chatroomViewModel: ChatroomViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityCommentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginViewModel.getAddressHeader(this)
        post = intent.extras!!.get("post") as Post
       /* val manager = ReviewManagerFactory.create(applicationContext)
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {

                val reviewInfo = task.result
            } else {
                val errorCode = when (val exception = task.exception) {
                    is ReviewException -> {
                        exception.errorCode
                    }
                    is RuntimeExecutionException -> {
                        exception.message
                    }
                    else -> {
                        9999
                    }
                }
            }
        }*/





        binding.txtLike.setOnClickListener(View.OnClickListener {
            val LikeUnlikeRequest = PostLikesRequest()
            LikeUnlikeRequest.UserId = user.UserId
            LikeUnlikeRequest.PostId = post.postId
            postsViewModel.LikeUnlikePost(LikeUnlikeRequest)
            if (post.isLiked == 0) {
                binding.txtLike.setTextColor(resources.getColor(R.color.Red))
                post.isLiked = 1

            } else {
                binding.txtLike.setTextColor(resources.getColor(R.color.Gray))
                post.isLiked = 0
            }
        })

        linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = linearLayoutManager
        linearLayoutManager.stackFromEnd = true
        binding.recyclerView.itemAnimator = null;
        adapter = CommentsAdapter(applicationContext, commentList)
        binding.recyclerView.adapter = adapter
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


                        var commentRequest = CommentRequest()
                        commentRequest.CommentContent = binding.etMessage.text!!.trim().toString()
                        commentRequest.UserId = user.UserId
                        commentRequest.PostId = post.postId
                        commentRequest.UserName = user.Name
                        commentRequest.image = user.Image
                        commentRequest.type = 1
                        commentRequest.commentImage = null
                        commentRequest.DateTime = System.currentTimeMillis()
                        if (post.users.UserId.toString() == user.UserId.toString()) {
                            commentRequest.IsOwner = 1
                        } else {
                            commentRequest.IsOwner = 0
                        }

                        commentsViewModel.addPostGroup(commentRequest)
                        binding.etMessage.text!!.clear()

                    }

                    override fun onAnimationCancel(animator: Animator) {}
                    override fun onAnimationRepeat(animator: Animator) {}
                })


            }

        })
        binding.txtSendImage.setOnClickListener(View.OnClickListener {

            ImagePicker.with(this)

                .provider(ImageProvider.BOTH).crop(16f, 9f)
                .createIntentFromDialog { launcher.launch(it) }

        })


        initObserver()
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

      /*  binding.toolbar.setNavigationOnClickListener(View.OnClickListener {
            val flow = manager.launchReviewFlow(this, reviewInfo)
            flow.addOnCompleteListener { _ ->
                onBackPressed()
            }


        })*/
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_directMessage -> {

                    val addChatroomRequest = AddChatroomRequest()
                    addChatroomRequest.PostId = post.postId
                    addChatroomRequest.Reciever = post.users.UserId
                    addChatroomRequest.Sender = user.UserId
                    chatroomViewModel.CreateChatRoom(addChatroomRequest)

                    true
                }

                else -> false
            }
        }
        loginViewModel.getLoggedInUser();


    }

    fun initObserver() {
        loginViewModel.userDetails.observe(this, Observer {
            user = it
            user.UserId?.let { it1 -> postsViewModel.GetPostLikes(post.postId, it1) }

        })
        postsViewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })

        postsViewModel.AddPostViews(post.postId)
        postsViewModel.addPostLikesResponse.observe(this, Observer {
            Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
        })

        commentsViewModel.commentsModelList.observe(this, Observer {
            if (it.isEmpty()) {
                binding.txtNoComment.visibility = View.VISIBLE
            } else {

                binding.txtNoComment.visibility = View.GONE

            }
            adapter.updateEmployeeListItems(it.toMutableList())
            binding.recyclerView.smoothScrollToPosition(it.size);
        })
        commentsViewModel.getSavedAddresses(post.postId)
        postsViewModel.getPostLikesResponse.observe(this, Observer {
            post.isLiked = it.results.data
            if (it.results.data == 0) {
                binding.txtLike.setTextColor(resources.getColor(R.color.Gray))

            } else {
                binding.txtLike.setTextColor(resources.getColor(R.color.Red))
            }
        })
        commentsViewModel.commentImageUploadResponse.observe(this, Observer {

            if (it?.message == "Image Uploaded") {

                var commentRequest = CommentRequest()
                commentRequest.CommentContent = binding.etMessage.text!!.trim().toString()
                commentRequest.UserId = user.UserId
                commentRequest.PostId = post.postId
                commentRequest.UserName = user.Name
                commentRequest.image = user.Image
                commentRequest.DateTime = System.currentTimeMillis()
                if (post.users.UserId.toString() == user.UserId.toString()) {
                    commentRequest.IsOwner = 1
                } else {
                    commentRequest.IsOwner = 0
                }
                commentRequest.type = 2
                commentRequest.commentImage = it.results.data

                commentsViewModel.addPostGroup(commentRequest)
                binding.etMessage.text!!.clear()

            } else {

            }
        })

        chatroomViewModel.chatRoomCreateResponse.observe(this, Observer {
            val intent = Intent(this, DirectChatActivity::class.java)




            intent.putExtra("chatId", it.results.data._id)
            intent.putExtra("postId", it.results.data.postId)
            intent.putExtra("reciever",post.users.UserId)
            intent.putExtra("sender", user.UserId)

            startActivity(intent)
        })
        chatroomViewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })

    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!

                file = uri.toFile()
                commentsViewModel.AddCommentImage(file)

            }
        }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
}