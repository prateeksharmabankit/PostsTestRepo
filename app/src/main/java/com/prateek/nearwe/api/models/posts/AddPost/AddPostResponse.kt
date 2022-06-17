/*************************************************
 * Created by Efendi Hariyadi on 17/06/22, 9:25 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 17/06/22, 9:25 PM
 ************************************************/

package com.prateek.nearwe.api.models.posts.AddPost

import com.prateek.nearwe.api.models.posts.PostSubCategoryDetailsDto
import java.io.Serializable


data class AddPostResponse(
    val IsError: Boolean,
    val ErrorMessage: String,
    val Result: Int,
    val Message: String,
) : Serializable