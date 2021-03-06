/*************************************************
 * Created by Efendi Hariyadi on 29/06/22, 9:55 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 29/06/22, 9:55 AM
 ************************************************/

package com.prateek.nearwe.api.models.posts.AppPostLikesResponse

data class AddPostLikesResponse(
    val code: Int,
    val error: Boolean,
    val message: String,
    val results: Results
)