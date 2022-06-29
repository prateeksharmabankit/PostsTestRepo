/*************************************************
 * Created by Efendi Hariyadi on 14/06/22, 11:48 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 14/06/22, 11:48 AM
 ************************************************/

package com.prateek.nearwe.api.models.posts.PostLikes

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class PostLikesRequest(
    @SerializedName("postId") var PostId: Int? = null,
    @SerializedName("userId") var UserId: Int? = null,
)