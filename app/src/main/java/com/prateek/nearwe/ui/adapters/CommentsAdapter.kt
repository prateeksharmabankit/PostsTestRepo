/*************************************************
 * Created by Efendi Hariyadi on 02/06/22, 4:14 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 02/06/22, 4:14 PM
 ************************************************/

package com.prateek.nearwe.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.prateek.nearwe.R

import com.prateek.nearwe.api.models.Comments.CommentsModel

class CommentsAdapter(

    private val commentsModelList: List<CommentsModel>) : RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_comments_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = commentsModelList[position]
        holder.txtname.text = comment.name
        holder.txtemail.text = comment.email
        holder.txtBody.text = comment.body
    }


    override fun getItemCount(): Int {
        return commentsModelList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val txtname: TextView = itemView.findViewById(R.id.txtname)
        val txtemail: TextView = itemView.findViewById(R.id.txtemail)
        val txtBody: TextView = itemView.findViewById(R.id.txtBody)

    }
}
