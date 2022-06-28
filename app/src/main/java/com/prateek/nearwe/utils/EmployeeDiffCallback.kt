/*************************************************
 * Created by Efendi Hariyadi on 23/06/22, 11:43 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 23/06/22, 11:43 AM
 ************************************************/

package com.prateek.nearwe.utils

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.prateek.nearwe.api.models.posts.postresponse.Post


class EmployeeDiffCallback(oldEmployeeList: List<Post>, newEmployeeList: List<Post>) :
    DiffUtil.Callback() {
    private val mOldEmployeeList: List<Post>
    private val mNewEmployeeList: List<Post>
    override fun getOldListSize(): Int {
        return mOldEmployeeList.size
    }

    override fun getNewListSize(): Int {
        return mNewEmployeeList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldEmployeeList[oldItemPosition].postId == mNewEmployeeList[newItemPosition].postId&&mOldEmployeeList[oldItemPosition].postViews == mNewEmployeeList[newItemPosition].postViews
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldEmployeeList.get(oldItemPosition).equals(mNewEmployeeList.get(newItemPosition));
    }

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

    init {
        mOldEmployeeList = oldEmployeeList
        mNewEmployeeList = newEmployeeList
    }
}