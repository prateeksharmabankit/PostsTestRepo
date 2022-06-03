package com.numesa.android.simpeldesa.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.numesa.android.simpeldesa.R
import com.numesa.android.simpeldesa.ui.adapters.FavouritesAdapter
import com.numesa.android.simpeldesa.ui.adapters.PostsAdapter
import com.numesa.android.simpeldesa.ui.posts.PostsViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FavouritesFragment : Fragment() {
    private val favouritesViewModel: PostsViewModel by sharedViewModel()
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

        initObserver()
        return root
    }

    private fun initObserver() {
        favouritesViewModel.userFavourites.observe(viewLifecycleOwner, Observer { listUser ->
            recyclerView.setItemViewCacheSize(listUser.size);
            if (listUser.isEmpty()) {
                txtNoPost.visibility = View.VISIBLE
            } else {
                txtNoPost.visibility = View.GONE
            }
            val adapter = FavouritesAdapter(listUser)

            recyclerView.adapter = adapter
        })
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
        fun newInstance(sectionNumber: Int): FavouritesFragment {
            return FavouritesFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}