/*************************************************
 * Created by Efendi Hariyadi on 14/06/22, 7:06 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 14/06/22, 7:06 PM
 ************************************************/

package com.prateek.nearwe.api.models.Comments.CommentRequest

import com.google.gson.annotations.SerializedName


data class CommentRequest(

    @SerializedName("postId") var PostId: Int? = null,
    @SerializedName("userId") var UserId: Int? = null,
    @SerializedName("commentContent") var CommentContent: String? = null,
    @SerializedName("userName") var UserName: String? = null,
    @SerializedName("dateTime") var DateTime: Long? = null,
    @SerializedName("isOwner") var IsOwner: Int? = null,
    )

