/*************************************************
 * Created by Efendi Hariyadi on 29/06/22, 9:31 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 29/06/22, 9:31 AM
 ************************************************/

package com.prateek.nearwe.api.models.posts.postlikedstatus

data class PostLikedStatus(
    val code: Int,
    val error: Boolean,
    val message: String,
    val results: Results
)