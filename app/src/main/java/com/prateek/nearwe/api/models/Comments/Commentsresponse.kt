/*************************************************
 * Created by Efendi Hariyadi on 15/06/22, 5:48 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 13/06/22, 1:32 PM
 ************************************************/

package com.prateek.nearwe.api.models.Comments

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Commentsresponse(
    @SerializedName("UserId") var UserId: Int? = null,
    @SerializedName("PostId") var PostId: Int? = null,
    @SerializedName("CommentContent") var CommentContent: String? = null,
    @SerializedName("TimeStamp") var TimeStamp: String? = null,
    @SerializedName("UserName") var UserName: String? = null,
)
