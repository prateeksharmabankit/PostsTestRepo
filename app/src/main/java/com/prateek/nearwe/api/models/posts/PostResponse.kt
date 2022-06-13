/*************************************************
 * Created by Efendi Hariyadi on 12/06/22, 2:24 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 12/06/22, 2:24 PM
 ************************************************/

package com.prateek.nearwe.api.models.posts

data class PostResponse(
    val ErrorMessage: Any,
    val IsError: Boolean,
    val Message: Any,
    val Result: List<Result>
)