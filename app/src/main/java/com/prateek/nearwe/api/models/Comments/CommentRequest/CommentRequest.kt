/*************************************************
 * Created by Efendi Hariyadi on 14/06/22, 7:06 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 14/06/22, 7:06 PM
 ************************************************/

package com.prateek.nearwe.api.models.Comments.CommentRequest

import com.google.gson.annotations.SerializedName


data class CommentRequest(
    @SerializedName("PostId") var PostId: Int? = null,
    @SerializedName("UserId") var UserId: Int? = null,
    @SerializedName("CommentContent") var CommentContent: String? = null,

    )