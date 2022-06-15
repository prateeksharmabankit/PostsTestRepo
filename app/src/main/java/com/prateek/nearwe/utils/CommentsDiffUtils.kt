/*************************************************
 * Created by Efendi Hariyadi on 15/06/22, 11:50 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 15/06/22, 11:50 PM
 ************************************************/

package com.prateek.nearwe.utils

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.prateek.nearwe.api.models.Comments.CommentRequest.CommentRequest

class CommentsDiffUtils(private val oldList: List<CommentRequest>, private val newList: List<CommentRequest>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].CommentContent === newList.get(newItemPosition).CommentContent
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        val (_, value, name) = oldList[oldPosition]
        val (_, value1, name1) = newList[newPosition]

        return name == name1 && value == value1
    }

    @Nullable
    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }
}