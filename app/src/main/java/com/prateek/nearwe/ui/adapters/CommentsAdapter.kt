/*************************************************
 * Created by Efendi Hariyadi on 02/06/22, 4:14 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 02/06/22, 4:14 PM
 ************************************************/

package com.prateek.nearwe.ui.adapters

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.prateek.nearwe.R
import com.prateek.nearwe.api.models.Comments.CommentRequest.CommentRequest
import com.prateek.nearwe.utils.Utils


class CommentsAdapter(

    private val commentsModelList: List<CommentRequest>
) : RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_comments_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = commentsModelList[position]
        if (comment.IsOwner == 0) {
            holder.txtname.text = comment.UserName

        } else {
            holder.txtname.text = "Author"
        }
        holder.txtComment.text = comment.CommentContent
        holder.txtTime.text = Utils.CompanionClass.ConvertTimeStampintoAgo(comment.DateTime)
    }


    override fun getItemCount(): Int {
        return commentsModelList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val txtComment: TextView = itemView.findViewById(R.id.txtComment)
        val txtname: TextView = itemView.findViewById(R.id.txtname)
        val txtTime: TextView = itemView.findViewById(R.id.txtTime)

    }

}
