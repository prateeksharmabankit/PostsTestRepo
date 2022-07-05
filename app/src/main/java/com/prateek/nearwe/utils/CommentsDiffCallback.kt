/*************************************************
 * Created by Efendi Hariyadi on 23/06/22, 11:43 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 23/06/22, 11:43 AM
 ************************************************/

package com.prateek.nearwe.utils

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.prateek.nearwe.api.models.Comments.CommentRequest.CommentRequest
import com.prateek.nearwe.api.models.posts.postresponse.Post


class CommentsDiffCallback(oldEmployeeList: List<CommentRequest>, newEmployeeList: List<CommentRequest>) :
    DiffUtil.Callback() {
    private val mOldEmployeeList: List<CommentRequest>
    private val mNewEmployeeList: List<CommentRequest>
    override fun getOldListSize(): Int {
        return mOldEmployeeList.size
    }

    override fun getNewListSize(): Int {
        return mNewEmployeeList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldEmployeeList[oldItemPosition].DateTime == mNewEmployeeList[newItemPosition].DateTime&&mOldEmployeeList[oldItemPosition].CommentContent == mNewEmployeeList[newItemPosition].CommentContent
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldEmployeeList.get(oldItemPosition).equals(mNewEmployeeList.get(newItemPosition));
    }

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

    init {
        mOldEmployeeList = oldEmployeeList
        mNewEmployeeList = newEmployeeList
    }
}