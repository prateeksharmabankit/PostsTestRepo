/*************************************************
 * Created by Efendi Hariyadi on 02/06/22, 4:14 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 02/06/22, 4:14 PM
 ************************************************/

package com.prateek.nearwe.ui.adapters


import android.app.Activity
import android.graphics.Typeface
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.view.ContextThemeWrapper
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.chip.Chip
import com.prateek.nearwe.R
import com.prateek.nearwe.api.models.chatrooms.chatroomresponses.Chatcontent
import com.prateek.nearwe.api.models.chatrooms.chatroomresponses.ChatroomResponse
import com.prateek.nearwe.api.models.chatrooms.chatroomresponses.Data
import com.prateek.nearwe.application.MainApp
import com.prateek.nearwe.utils.ChatroomDiffCallback
import com.prateek.nearwe.utils.Utils.CompanionClass.Companion.ifNonNull
import com.prateek.nearwe.utils.Utils.CompanionClass.Companion.ifNull


class ChatroomAdapter(

    private val onItemClickListener: OnItemClickListener,
    private val mList: MutableList<Data>,
    private val activity: Activity,
    private val userId: Int?
) :
    RecyclerView.Adapter<ChatroomAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_chatroom_row, parent, false)

        return ViewHolder(view)
    }


    class OnItemClickListener(val clickListener: (meme: Data) -> Unit) {
        fun onClick(meme: Data) = clickListener(meme)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val post = mList[position]
        holder.txtTitle.text = post.chatcontent.message
        if (post.sender == userId) {
            holder.txtName.text = "You"
            Glide.with(MainApp.instance)

                .load(post.senderuser.image)
                .placeholder(MainApp.instance.resources.getDrawable(R.drawable.ic_user))
                .circleCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imgUser)
        } else {
            holder.txtName.text = post.recieveruser.name
            Glide.with(MainApp.instance)

                .load(post.recieveruser.image)
                .placeholder(MainApp.instance.resources.getDrawable(R.drawable.ic_user))
                .circleCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imgUser)
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
        val txtName: TextView = itemView.findViewById(R.id.txtName)
        val imgUser: ImageView = itemView.findViewById(R.id.imgUser)


    }

    fun updateEmployeeListItems(employees: MutableList<Data>) {
        val diffCallback = ChatroomDiffCallback(this.mList, employees!!)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        mList.clear()
        mList.addAll(employees)
        diffResult.dispatchUpdatesTo(this)
    }
}
