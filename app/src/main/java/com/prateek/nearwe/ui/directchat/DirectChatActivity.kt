/*************************************************
 * Created by Efendi Hariyadi on 03/06/22, 10:45 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 03/06/22, 10:37 AM
 ************************************************/


package com.prateek.nearwe.ui.directchat

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.prateek.nearwe.api.models.User.UserModel
import com.prateek.nearwe.api.models.chatrooms.addchatcontent.AddChatcontentRequest
import com.prateek.nearwe.api.models.chatrooms.chatcontent.Data
import com.prateek.nearwe.databinding.ActivityDirectchatBinding
import com.prateek.nearwe.ui.adapters.OneTwoOneChatAdapter
import com.prateek.nearwe.ui.login.LoginViewModel
import kotlinx.android.synthetic.main.activity_comments.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


class DirectChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDirectchatBinding
    private val commentsViewModel: DirectChatViewModel by viewModel()
    private lateinit var linearLayoutManager: LinearLayoutManager

    private val loginViewModel: LoginViewModel by viewModel()

    private lateinit var file: File
    var Name: String = ""
    var userId: Int = 0
    var chatId: String? = ""
    var postId: Int = 0
    var reciever: Int = 0

    private val commentList = ArrayList<Data>()
    private lateinit var adapter: OneTwoOneChatAdapter
    private lateinit var user: UserModel



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityDirectchatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginViewModel.getAddressHeader(this)






        linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = linearLayoutManager
        linearLayoutManager.stackFromEnd = true
        binding.recyclerView.itemAnimator = null;

        userId=intent.extras!!.getInt("sender")
        adapter = OneTwoOneChatAdapter(applicationContext, commentList,userId)
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


                        val commentRequest = AddChatcontentRequest()
                        commentRequest.Message = binding.etMessage.text!!.trim().toString()
                        commentRequest.Sender = user.UserId
                        commentRequest.PostId = postId
                        commentRequest.chatId = chatId.toString()
                        commentRequest.Reciever=reciever
                        commentsViewModel.AddChatConent(commentRequest)

                        binding.etMessage.text!!.clear()

                    }

                    override fun onAnimationCancel(animator: Animator) {}
                    override fun onAnimationRepeat(animator: Animator) {}
                })


            }

        })


        initObserver()
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.toolbar.setNavigationOnClickListener(View.OnClickListener {
            onBackPressed()
        })
        loginViewModel.getLoggedInUser();
    }

    fun initObserver() {
        loginViewModel.userDetails.observe(this, Observer {
            user = it
            chatId=intent.extras!!.getString("chatId")
            postId=intent.extras!!.getInt("postId")
            reciever=intent.extras!!.getInt("reciever")
            commentsViewModel.getMyChatContent(chatId)

        })



        commentsViewModel.postList.observe(this, Observer {
            if (it.results.data.isEmpty()) {
                binding.txtNoComment.visibility = View.VISIBLE
            } else {

                binding.txtNoComment.visibility = View.GONE

            }
            adapter.updateEmployeeListItems(it.results.data.toMutableList(),user.UserId)
            binding.recyclerView.smoothScrollToPosition(it.results.data.size);
        })





    }


}