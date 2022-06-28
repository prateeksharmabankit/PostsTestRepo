/*************************************************
 * Created by Efendi Hariyadi on 27/06/22, 11:22 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 27/06/22, 11:22 PM
 ************************************************/

package com.prateek.nearwe.api.models.posts.postresponse

import com.prateek.nearwe.api.models.User.UserModel
import java.io.Serializable

data class Post(
    val ago: String,
    val categoryName: String,
    val dateTimeStamp: String,
    val distance: Double,
    val isAnonymous: Int,
    val latitude: String,
    val longitude: String,
    val imageUrl: String,
    val postId: Int,
    val postType: Int,
    val postViews: Int,
    val subCategories: String,
    val title: String,
    val users: UserModel,
            var isLiked:Int
):Serializable