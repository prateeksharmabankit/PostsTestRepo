/*************************************************
 * Created by Efendi Hariyadi on 27/06/22, 11:22 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 27/06/22, 11:22 PM
 ************************************************/

package com.prateek.nearwe.api.models.posts.postresponse

data class PostResponse(
    val code: Int,
    val error: Boolean,
    val message: String,
    val results: Results
)