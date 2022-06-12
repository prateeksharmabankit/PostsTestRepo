/*************************************************
 * Created by Efendi Hariyadi on 02/06/22, 4:14 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 02/06/22, 4:14 PM
 ************************************************/

package com.prateek.nearwe.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.prateek.nearwe.R

import com.prateek.nearwe.api.models.posts.PostModel


class PostsAdapter(
    private val onCheckboxClickListener: OnClickListener,
    private val onItemClickListener: OnItemClickListener,
    private val mList: List<PostModel>
) :
    RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_posts_row, parent, false)

        return ViewHolder(view)
    }

    class OnClickListener(val clickListener: (meme: PostModel) -> Unit) {
        fun onClick(meme: PostModel) = clickListener(meme)
    }
    class OnItemClickListener(val clickListener: (meme: PostModel) -> Unit) {
        fun onClick(meme: PostModel) = clickListener(meme)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val post = mList[position]
        holder.txtTitle.text = post.title
        holder.txtBody.text = post.body
        holder.star.isChecked = post.isFavourite
        holder.star.setOnCheckedChangeListener { _, isChecked ->
            post.isFavourite = isChecked

            onCheckboxClickListener.onClick(post)
        }
        holder.itemView.setOnClickListener {

            onItemClickListener.onClick(post)
        }


    }


    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val txtTitle: TextView = itemView.findViewById(R.id.txtTitle)
        val txtBody: TextView = itemView.findViewById(R.id.txtBody)
        val star: CheckBox = itemView.findViewById(R.id.star)

    }
}
