/*************************************************
 * Created by Efendi Hariyadi on 02/06/22, 4:14 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 02/06/22, 4:14 PM
 ************************************************/

package com.prateek.nearwe.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.prateek.nearwe.R
import com.prateek.nearwe.api.models.Comments.CommentRequest.CommentRequest
import com.prateek.nearwe.application.MainApp
import com.prateek.nearwe.utils.CommentsDiffCallback
import com.prateek.nearwe.utils.Utils


class CommentsAdapter(
    context: Context,
    private val commentsModelList: MutableList<CommentRequest>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val context: Context = context


    private inner class View1ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val txtComment: TextView = itemView.findViewById(R.id.txtComment)
        val txtname: TextView = itemView.findViewById(R.id.txtname)
        val txtTime: TextView = itemView.findViewById(R.id.txtTime)
        val imgUser: ImageView = itemView.findViewById(R.id.imgUser)

        fun bind(position: Int) {
            val comment = commentsModelList.get(position)
            if (comment.IsOwner == 0) {
                txtname.text = comment.UserName

            } else {
                txtname.text = "Author"
            }
            txtComment.text = comment.CommentContent
            txtTime.text = Utils.CompanionClass.ConvertTimeStampintoAgo(comment.DateTime)
            Glide.with(MainApp.instance)

                .load(comment.image).placeholder(MainApp.instance.resources.getDrawable(R.drawable.ic_user))
                .apply(RequestOptions.bitmapTransform(RoundedCorners(25)))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imgUser)
        }
    }

    private inner class View2ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val txtComment: TextView = itemView.findViewById(R.id.txtComment)
        val txtname: TextView = itemView.findViewById(R.id.txtname)
        val txtTime: TextView = itemView.findViewById(R.id.txtTime)

        val imgUser: ImageView = itemView.findViewById(R.id.imgUser)

        fun bind(position: Int) {
            val comment = commentsModelList.get(position)
            if (comment.IsOwner == 0) {
                txtname.text = comment.UserName

            } else {
                txtname.text = "Author"
            }
            txtComment.text = comment.CommentContent
            txtTime.text = Utils.CompanionClass.ConvertTimeStampintoAgo(comment.DateTime)
            Glide.with(MainApp.instance)

                .load(comment.image).placeholder(MainApp.instance.resources.getDrawable(R.drawable.ic_user))
                .apply(RequestOptions.bitmapTransform(RoundedCorners(25)))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imgUser)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_ONE) {
            return View1ViewHolder(
                LayoutInflater.from(context)
                    .inflate(R.layout.recyclerview_comments_row, parent, false)
            )
        }
        return View2ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.recyclerview_comments_right_row, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return commentsModelList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (commentsModelList[position].IsOwner == VIEW_TYPE_ONE) {
            (holder as View1ViewHolder).bind(position)
        } else {
            (holder as View2ViewHolder).bind(position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (commentsModelList[position].IsOwner) {
            0 -> VIEW_TYPE_ONE
            else -> VIEW_TYPE_TWO
        }

    }

    companion object {
        const val VIEW_TYPE_ONE = 0
        const val VIEW_TYPE_TWO = 1
    }

    fun updateEmployeeListItems(employees: MutableList<CommentRequest>) {
        val diffCallback = CommentsDiffCallback(this.commentsModelList, employees!!)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        commentsModelList.clear()
        commentsModelList.addAll(employees)
        diffResult.dispatchUpdatesTo(this)
    }
}


