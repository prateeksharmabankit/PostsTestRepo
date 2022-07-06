/*************************************************
 * Created by Efendi Hariyadi on 05/07/22, 5:39 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 05/07/22, 5:39 PM
 ************************************************/

package com.prateek.nearwe.api.models.Comments.CommentRequest.commentimage

data class CommentImageUploadResponse(
    val code: Int,
    val error: Boolean,
    val message: String,
    val results: Results
)