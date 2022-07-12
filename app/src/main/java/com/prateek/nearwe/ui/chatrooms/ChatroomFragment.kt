package com.prateek.nearwe.ui.chatrooms

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.prateek.nearwe.api.models.User.UserModel
import com.prateek.nearwe.api.models.chatrooms.chatroomresponses.Chatcontent
import com.prateek.nearwe.api.models.chatrooms.chatroomresponses.ChatroomResponse
import com.prateek.nearwe.api.models.chatrooms.chatroomresponses.Data
import com.prateek.nearwe.api.models.posts.postresponse.Post
import com.prateek.nearwe.databinding.FragmentChatroomBinding
import com.prateek.nearwe.ui.adapters.ChatroomAdapter
import com.prateek.nearwe.ui.adapters.PostsAdapter
import com.prateek.nearwe.ui.directchat.DirectChatActivity
import com.prateek.nearwe.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatroomFragment : Fragment() {
    private lateinit var binding: FragmentChatroomBinding
    private val chatroomViewModel: ChatroomViewModel by viewModel()
    private val userViewModel: LoginViewModel by viewModel()

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var user: UserModel
    private val postList = ArrayList<Data>()
    private lateinit var postAdapter: ChatroomAdapter
    private var userId=0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentChatroomBinding.inflate(inflater, container, false)


        initObserver()
        userViewModel.getLoggedInUser()
        return binding.root

    }
    private fun initUI() {
        linearLayoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = linearLayoutManager
        postAdapter = ChatroomAdapter( ChatroomAdapter.OnItemClickListener { posts ->
            val intent = Intent(activity, DirectChatActivity::class.java)
            intent.putExtra("chatId",posts._id)
            intent.putExtra("postId", posts.postId)
            intent.putExtra("sender", posts.sender)
            intent.putExtra("reciever", posts.reciever)
            startActivity(intent)



        }, postList,requireActivity(),user.UserId)
        binding.recyclerView.adapter = postAdapter
        binding.recyclerView.itemAnimator = null;

    }

    fun initObserver() {

        chatroomViewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        }

        chatroomViewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })

        userViewModel.userDetails.observe(viewLifecycleOwner, Observer {
            user = it
            userId= user.UserId!!
            initUI()
            chatroomViewModel.getMyChats(user.UserId)

        })
        chatroomViewModel.chatRoomList.observe(viewLifecycleOwner, Observer {
                it?.let { list ->
                    binding.progressBar.visibility = View.GONE
                    postAdapter.updateEmployeeListItems(list.results.data.toMutableList())
                }

            })





    }


}