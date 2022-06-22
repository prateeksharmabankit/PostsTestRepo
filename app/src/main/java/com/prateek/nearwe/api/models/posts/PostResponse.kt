/*************************************************
 * Created by Efendi Hariyadi on 22/06/22, 1:42 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 22/06/22, 1:42 PM
 ************************************************/

package com.prateek.nearwe.api.models.posts

import androidx.room.Entity

@Entity
data class PostResponse(
    val errorMessage: String,
    val isError: Boolean,
    val message: String,
    val result: List<Result>
)