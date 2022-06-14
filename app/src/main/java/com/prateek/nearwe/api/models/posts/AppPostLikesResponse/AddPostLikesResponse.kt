/*************************************************
 * Created by Efendi Hariyadi on 14/06/22, 11:07 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 14/06/22, 11:07 AM
 ************************************************/

package com.prateek.nearwe.api.models.posts.AppPostLikesResponse

data class AddPostLikesResponse(
    val ErrorMessage: Any,
    val IsError: Boolean,
    val Message: Any,
    val Result: Int
)