package com.prateek.nearwe.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prateek.nearwe.R

import com.prateek.nearwe.ui.adapters.FavouritesAdapter
import com.prateek.nearwe.ui.posts.PostsViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FavouritesFragment : Fragment() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var txtNoPost: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_favourite, container, false)
        recyclerView = root.findViewById(R.id.recyclerView)
        txtNoPost = root.findViewById(R.id.txtNoPost)
        linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager

      //  initObserver()
        return root
    }

    private fun initObserver() {
       /* favouritesViewModel.AddPostViews().observe(viewLifecycleOwner, Observer { listUser ->
            recyclerView.setItemViewCacheSize(listUser.size);
            if (listUser.isEmpty()) {
                txtNoPost.visibility = View.VISIBLE
            } else {
                txtNoPost.visibility = View.GONE
            }
            val adapter = FavouritesAdapter(listUser)

            recyclerView.adapter = adapter
        })*/
    }


}