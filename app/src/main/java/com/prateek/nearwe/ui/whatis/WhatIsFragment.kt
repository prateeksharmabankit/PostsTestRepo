package com.prateek.nearwe.ui.whatis

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.birjuvachhani.locus.Locus
import com.prateek.nearwe.R
import com.prateek.nearwe.api.models.User.UserModel

import com.prateek.nearwe.ui.adapters.PostsAdapter
import com.prateek.nearwe.ui.comments.CommentsActivity
import com.prateek.nearwe.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.Serializable

class WhatIsFragment : Fragment() {
    private val whatIsViewModel: WhatIsViewModel by viewModel()
    private val userViewModel: LoginViewModel by viewModel()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var txtHeader: TextView
    private lateinit var user: UserModel

    var latitude: String = ""
    var longitude: String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_whatis, container, false)

        recyclerView = root.findViewById(R.id.recyclerView)

        progressBar = root.findViewById(R.id.progressBar)
        linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager
        txtHeader = root.findViewById(R.id.txtHeader)
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

        return root

    }


    fun initObserver() {
        whatIsViewModel.userList.observe(viewLifecycleOwner) {
            Log.e("error",it.Result.get(0).IsLiked.toString())
            val adapter = PostsAdapter(PostsAdapter.OnClickListener { post ->


            }, PostsAdapter.OnItemClickListener { post ->
                val intent = Intent(activity, CommentsActivity::class.java)
                intent.putExtra("post", post as Serializable)
                intent.putExtra("addressDetails", txtHeader.text)
                startActivity(intent)

            }, it.Result)

            recyclerView.adapter = adapter

        }

        whatIsViewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        }

        whatIsViewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        })


        userViewModel.userDetails.observe(viewLifecycleOwner, Observer {
            user=it
            whatIsViewModel.getWhatIsPosts(it.UserId, latitude, longitude)
        })


        userViewModel.addressDetails.observe(viewLifecycleOwner, Observer {
            txtHeader.text = it
        })
        whatIsViewModel.addPostViewResponse.observe(viewLifecycleOwner, Observer {
            whatIsViewModel.getWhatIsPosts(user.UserId, latitude, longitude)
        })

    }


}