/*************************************************
 * Created by Efendi Hariyadi on 12/06/22, 2:24 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 12/06/22, 2:24 PM
 ************************************************/

package com.prateek.nearwe.api.models.posts

import java.io.Serializable

data class Result(
    val Ago: String,
    val Distance: String,
    val IsAnonymous: Int,
    val Latitude: String,
    val Longitude: String,
    val PostId: Int,
    val PostType: Int,
    val PostViews: Int,
    val Title: String,
    val UserId: String,
    val Name: String,
    val EmailAddress: String,
    var IsLiked:Int,
    val postSubCategoryDetailsDtos: List<PostSubCategoryDetailsDto>
): Serializable