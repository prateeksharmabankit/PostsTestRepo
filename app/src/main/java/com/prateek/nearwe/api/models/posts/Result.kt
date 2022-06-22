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
    val emailAddress: Any,
    val imageUrl: Any,
    val isAnonymous: Int,
    var isLiked: Int,
    val latitude: String,
    val longitude: String,
    val name: Any,
    val postId: Int,
    val postSubCategories: String,
    val postSubCategoryDetailsDtos: List<PostSubCategoryDetailsDtoX>,
    val postType: Int,
    val postViews: Int,
    val title: String,
    val user: User,
    val userId: Int
):Serializable