/*************************************************
 * Created by Efendi Hariyadi on 02/06/22, 4:14 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 02/06/22, 4:14 PM
 ************************************************/

package com.prateek.nearwe.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.prateek.nearwe.R
import com.prateek.nearwe.api.models.SubCategory.Result


class SubCategoryAdapter(

    private val commentsModelList: List<Result>
) : RecyclerView.Adapter<SubCategoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_subcategory_chips, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chip = commentsModelList[position]
        holder.chip1.text = chip.Value
        holder.chip1.tag = position

        holder.chip1.setOnClickListener(View.OnClickListener {
            try {
                val pos = holder.chip1.tag as Int
                commentsModelList[pos].isCHecked = !commentsModelList[pos].isCHecked
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
    }


    override fun getItemCount(): Int {
        return commentsModelList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val chip1: Chip = itemView.findViewById(R.id.chip1)


    }

}
