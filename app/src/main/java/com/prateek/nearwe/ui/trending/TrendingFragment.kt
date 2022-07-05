package com.prateek.nearwe.ui.trending

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.birjuvachhani.locus.Locus
import com.prateek.nearwe.api.models.User.UserModel
import com.prateek.nearwe.api.models.posts.postresponse.Post
import com.prateek.nearwe.databinding.FragmentTrendingBinding
import com.prateek.nearwe.ui.adapters.PostsAdapter
import com.prateek.nearwe.ui.comments.CommentsActivity
import com.prateek.nearwe.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.Serializable

class TrendingFragment : Fragment() {
    private lateinit var binding: FragmentTrendingBinding
    private val trendingViewModel: TrendingViewModel by viewModel()
    private val userViewModel: LoginViewModel by viewModel()
    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var user: UserModel

    var latitude: String = ""
    var longitude: String = ""
    private val postList = ArrayList<Post>()
    private lateinit var postAdapter: PostsAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrendingBinding.inflate(inflater, container, false)
        initUI()
        initObserver()
        userViewModel.getLoggedInUser()
        return binding.root

    }

    private fun initUI() {
        linearLayoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = linearLayoutManager
        postAdapter = PostsAdapter(PostsAdapter.OnItemClickListener { post ->
            val intent = Intent(activity, CommentsActivity::class.java)
            intent.putExtra("post", post as Serializable)
            intent.putExtra("UserId", user.UserId)
            intent.putExtra("Name", user.Name)
            startActivity(intent)

        }, postList,requireActivity())

        binding.recyclerView.adapter = postAdapter
        binding.recyclerView.itemAnimator = null;
    }
    fun initObserver() {
        trendingViewModel.userList.observe(viewLifecycleOwner) {
            it?.let { list ->
                binding.progressBar.visibility = View.GONE
                postAdapter.updateEmployeeListItems(list.results.data.toMutableList())
            }
        }

        trendingViewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        }

        trendingViewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })


        userViewModel.userDetails.observe(viewLifecycleOwner, Observer {
            user = it
            trendingViewModel.getAllTrendingPosts(it.UserId)
        })

    }


}