/*************************************************
 * Created by Efendi Hariyadi on 11/07/22, 11:18 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 11/07/22, 11:18 AM
 ************************************************/

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
import com.prateek.nearwe.api.models.chatrooms.chatcontent.Data
import com.prateek.nearwe.application.MainApp
import com.prateek.nearwe.utils.ChatContentDiffCallback
import com.prateek.nearwe.utils.CommentsDiffCallback
import com.prateek.nearwe.utils.Utils
import com.prateek.nearwe.utils.Utils.CompanionClass.Companion.ifNonNull
import com.prateek.nearwe.utils.Utils.CompanionClass.Companion.ifNull


class OneTwoOneChatAdapter(
    context: Context,
    private val commentsModelList: MutableList<Data>, private var userId: Int?
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val context: Context = context


    private inner class View1ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val txtComment: TextView = itemView.findViewById(R.id.txtComment)
        val txtname: TextView = itemView.findViewById(R.id.txtname)
        val txtTime: TextView = itemView.findViewById(R.id.txtTime)
        val imgUser: ImageView = itemView.findViewById(R.id.imgUser)
        val imgComment: ImageView = itemView.findViewById(R.id.imgComment)
        fun bind(position: Int) {
            val comment = commentsModelList.get(position)
            txtname.text=comment.recieveruser.name
            txtComment.text = comment.message
            txtTime.text = comment.ago

            Glide.with(MainApp.instance)

                 .load(comment.recieveruser.image).placeholder(MainApp.instance.resources.getDrawable(R.drawable.ic_user))
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
        val imgComment: ImageView = itemView.findViewById(R.id.imgComment)

        fun bind(position: Int) {
            val comment = commentsModelList.get(position)
            txtname.text=comment.senderuser.name
            txtComment.text = comment.message
            Glide.with(MainApp.instance)

                .load(comment.senderuser.image).placeholder(MainApp.instance.resources.getDrawable(R.drawable.ic_user))
                .apply(RequestOptions.bitmapTransform(RoundedCorners(25)))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imgUser)
            txtTime.text = comment.ago


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
        if (commentsModelList[position].sender == userId) {
            (holder as View1ViewHolder).bind(position)
        } else {
            (holder as View2ViewHolder).bind(position)
        }
    }

    override fun getItemViewType(position: Int): Int {

        return if(commentsModelList[position].sender==userId) {
            VIEW_TYPE_ONE
        } else {
            VIEW_TYPE_TWO
        }


    }

    companion object {
        const val VIEW_TYPE_ONE = 0
        const val VIEW_TYPE_TWO = 1
    }

    fun updateEmployeeListItems(employees: MutableList<Data>,users:Int?) {
        val diffCallback = ChatContentDiffCallback(this.commentsModelList, employees!!)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        commentsModelList.clear()
        commentsModelList.addAll(employees)
        diffResult.dispatchUpdatesTo(this)
        userId=users
    }
}


