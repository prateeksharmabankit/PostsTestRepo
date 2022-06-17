package com.prateek.nearwe.ui.posts

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.birjuvachhani.locus.Locus
import com.prateek.nearwe.R
import com.prateek.nearwe.api.models.User.UserModel
import com.prateek.nearwe.databinding.FragmentPostsBinding

import com.prateek.nearwe.ui.adapters.PostsAdapter
import com.prateek.nearwe.ui.comments.CommentsActivity
import com.prateek.nearwe.ui.login.LoginViewModel
import kotlinx.android.synthetic.main.nav_header_main.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.Serializable

class PostsFragment : Fragment() {
    private lateinit var binding: FragmentPostsBinding
    private val postsViewModel: PostsViewModel by viewModel()
    private val userViewModel: LoginViewModel by viewModel()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var user: UserModel
    var latitude: String = ""
    var longitude: String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostsBinding.inflate(inflater, container, false)
        linearLayoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = linearLayoutManager

        initObserver()
        Locus.getCurrentLocation(requireActivity().applicationContext) { result ->
            result.location?.let {

                latitude = it.latitude.toString()
                longitude = it.longitude.toString()
                userViewModel.getAddressHeader(activity?.applicationContext, latitude, longitude)
                userViewModel.getLoggedInUser()

            }
            result.error?.let {

                Locus.getCurrentLocation(requireActivity().applicationContext) { result ->
                    result.location?.let {
                        latitude = it.latitude.toString()
                        longitude = it.longitude.toString()
                        userViewModel.getAddressHeader(
                            activity?.applicationContext,
                            latitude,
                            longitude
                        )
                        userViewModel.getLoggedInUser()
                    }
                    result.error?.let { /* Received error! */ }
                }
            }
        }

        return binding.root

    }


    fun initObserver() {
        postsViewModel.userList.observe(viewLifecycleOwner) {
            val adapter = PostsAdapter(PostsAdapter.OnClickListener { post ->
            }, PostsAdapter.OnItemClickListener { post ->
                val intent = Intent(activity, CommentsActivity::class.java)
                intent.putExtra("post", post as Serializable)
                intent.putExtra("addressDetails", binding.txtHeader.text)
                startActivity(intent)

            }, it.Result)

            binding.recyclerView.adapter = adapter

        }

        postsViewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        }

        postsViewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })


        userViewModel.userDetails.observe(viewLifecycleOwner, Observer {
            user = it
            postsViewModel.getAllPosts(it.UserId, latitude, longitude)
        })


        userViewModel.addressDetails.observe(viewLifecycleOwner, Observer {
            binding.txtHeader.text = it
        })
        postsViewModel.addPostViewResponse.observe(viewLifecycleOwner, Observer {
            postsViewModel.getAllPosts(user.UserId, latitude, longitude)
        })

    }


}