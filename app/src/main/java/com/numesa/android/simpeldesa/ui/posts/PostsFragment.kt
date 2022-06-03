package com.numesa.android.simpeldesa.ui.posts

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.numesa.android.simpeldesa.R
import com.numesa.android.simpeldesa.ui.adapters.PostsAdapter
import com.numesa.android.simpeldesa.ui.comments.CommentsActivity
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PostsFragment : Fragment() {
    private val postsViewModel: PostsViewModel by sharedViewModel()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = root.findViewById(R.id.recyclerView)
        progressBar = root.findViewById(R.id.progressBar)
        linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager

        initObserver()
        return root

    }



    private fun initObserver() {
        postsViewModel.userList.observe(viewLifecycleOwner) {
            recyclerView.setItemViewCacheSize(it.size);
            val adapter = PostsAdapter(PostsAdapter.OnClickListener { post ->
                postsViewModel.updateFavourite(post)
                if (post.isFavourite) {
                    Toast.makeText(activity, "Added to Favourite", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, "Removed from Favourite", Toast.LENGTH_SHORT).show()
                }

            }, PostsAdapter.OnItemClickListener { post ->
                val intent = Intent(activity, CommentsActivity::class.java)
                intent.putExtra("id", post.id)

                startActivity(intent)

            }, it)

            recyclerView.adapter = adapter

        }

        postsViewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        }

        postsViewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        })
        postsViewModel.getAllComments()

    }


    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PostsFragment {
            return PostsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}