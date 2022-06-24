/*************************************************
 * Created by Efendi Hariyadi on 22/06/22, 1:42 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 22/06/22, 1:42 PM
 ************************************************/

package com.prateek.nearwe.api.models.posts

import java.io.Serializable

data class Result(
    val ago: String,
    val dateTimeStamp: String,
    val distance: Double,
    val emailAddress: String,
    val imageUrl: String,
    val isAnonymous: Int,
    var isLiked: Int,
    val latitude: String,
    val longitude: String,
    val name: String,
    val postId: Int,

    val postType: Int,
    val postViews: Int,
    val title: String,
    val userId: Int,
    val categoryId: Int,


    val categoryName: String,
    val subCategories: String



):Serializable