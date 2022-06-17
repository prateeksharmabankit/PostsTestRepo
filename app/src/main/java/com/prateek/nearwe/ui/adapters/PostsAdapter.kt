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
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.prateek.nearwe.R

import com.prateek.nearwe.api.models.posts.PostModel
import com.prateek.nearwe.api.models.posts.Result
import com.prateek.nearwe.application.MainApp


class PostsAdapter(
    private val onCheckboxClickListener: OnClickListener,
    private val onItemClickListener: OnItemClickListener,
    private val mList: List<Result>
) :
    RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_posts_row, parent, false)

        return ViewHolder(view)
    }

    class OnClickListener(val clickListener: (meme: Result) -> Unit) {
        fun onClick(meme: Result) = clickListener(meme)
    }
    class OnItemClickListener(val clickListener: (meme: Result) -> Unit) {
        fun onClick(meme: Result) = clickListener(meme)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val post = mList[position]
        holder.txtTitle.text = post.Title
        holder.txtDateTime.text=post.Ago
        holder.txtName.text=post.Name
        /*when (post.PostType) {
            1 -> holder.imgpostType.setColorFilter(ContextCompat.getColor(co, R.color.COLOR_YOUR_COLOR), android.graphics.PorterDuff.Mode.MULTIPLY)ti
            2 -> print("x == 2")
            1 -> print("x == 2")
            else -> { // Note the block
                print("x is neither 1 nor 2")
            }
        }*/

        for (categoryDetails in post.postSubCategoryDetailsDtos) {
            holder.txtCategory.text=categoryDetails.CategoryName
            holder.txtSubCategory.text=holder.txtSubCategory.text.toString()+" "+categoryDetails.SubCategoryName
        }


        holder.itemView.setOnClickListener {

            onItemClickListener.onClick(post)
        }

        post.ImageUrl?.let {
            Glide.with(MainApp.instance)
                .load(post.ImageUrl)
                .into(holder.imgPost)
        }




    }


    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imgpostType: ImageView = itemView.findViewById(R.id.imgpostType)
        val txtName: TextView = itemView.findViewById(R.id.txtName)
        val txtDateTime: TextView = itemView.findViewById(R.id.txtDateTime)
        val txtTitle: TextView = itemView.findViewById(R.id.txtTitle)
        val txtCategory: TextView = itemView.findViewById(R.id.txtCategory)
        val txtSubCategory: TextView = itemView.findViewById(R.id.txtSubCategory)
        val imgPost: ImageView = itemView.findViewById(R.id.imgPost)



    }
}
