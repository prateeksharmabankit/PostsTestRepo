/*************************************************
 * Created by Efendi Hariyadi on 02/06/22, 4:14 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 02/06/22, 4:14 PM
 ************************************************/

package com.prateek.nearwe.ui.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.prateek.nearwe.R
import com.prateek.nearwe.api.models.posts.postresponse.Post
import com.prateek.nearwe.application.MainApp
import com.prateek.nearwe.utils.EmployeeDiffCallback
import com.prateek.nearwe.utils.Utils.CompanionClass.Companion.ifNonNull
import com.prateek.nearwe.utils.Utils.CompanionClass.Companion.ifNull


class PostsAdapter(
    private val onCheckboxClickListener: OnClickListener,
    private val onItemClickListener: OnItemClickListener,
    private val mList: MutableList<Post>


) :
    RecyclerView.Adapter<PostsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_posts_row, parent, false)

        return ViewHolder(view)
    }

    class OnClickListener(val clickListener: (meme: Post) -> Unit) {
        fun onClick(meme: Post) = clickListener(meme)
    }

    class OnItemClickListener(val clickListener: (meme: Post) -> Unit) {
        fun onClick(meme: Post) = clickListener(meme)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val post = mList[position]
        holder.txtTitle.text = post.title
        holder.txtDateTime.text = post.ago
        holder.txtViews.text=post.postViews.toString()+" Views"

        when (post.postType) {
            1 -> holder.imgpostType.setColorFilter(
                ContextCompat.getColor(
                    MainApp.instance,
                    R.color.Green
                ), android.graphics.PorterDuff.Mode.SRC_ATOP
            )
            2 -> holder.imgpostType.setColorFilter(
                ContextCompat.getColor(
                    MainApp.instance,
                    R.color.Orange
                ), android.graphics.PorterDuff.Mode.SRC_ATOP
            )
            3 -> holder.imgpostType.setColorFilter(
                ContextCompat.getColor(
                    MainApp.instance,
                    R.color.Red
                ), android.graphics.PorterDuff.Mode.SRC_ATOP
            )

        }

        holder.lvSubCategory.removeAllViews()
        val lstValues: List<String> = post.subCategories.split(",").map { it -> it.trim() }
        lstValues.forEach { it ->
            val chip = Chip(ContextThemeWrapper( MainApp.instance, R.style.Theme_MaterialComponents_Light))
            chip.text = it
            chip.setChipBackgroundColorResource(R.color.colorPrimary)


            chip.isCloseIconVisible = false
            chip.setTextColor(MainApp.instance.getColor(R.color.primarytext))
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
            )

            layoutParams.setMargins(0, 0, 10, 0)

            holder.lvSubCategory.addView(chip,layoutParams)
        }





        when (post.isAnonymous) {
            0 -> holder.txtName.text = post.users.Name

            1 -> holder.txtName.text = MainApp.instance.getString(R.string.anonymous)


        }

        post.imageUrl.ifNull {

            holder.imgPost.visibility = View.GONE
        }
        post.imageUrl.ifNonNull {
            Glide.with(MainApp.instance)
                .load(post.imageUrl)
                .into(holder.imgPost)
            holder.imgPost.visibility = View.VISIBLE
        }

      //  holder.txtCategory.text = post.categoryName

        holder.itemView.setOnClickListener {

            onItemClickListener.onClick(post)
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
        val lvSubCategory: LinearLayout = itemView.findViewById(R.id.lvSubCategory)
        val imgPost: ImageView = itemView.findViewById(R.id.imgPost)
        val txtViews: TextView = itemView.findViewById(R.id.txtViews)



    }

    fun updateEmployeeListItems(employees: MutableList<Post>) {
        val diffCallback = EmployeeDiffCallback(this.mList, employees!!)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        mList.clear()
        mList.addAll(employees)
        diffResult.dispatchUpdatesTo(this)
    }
}
