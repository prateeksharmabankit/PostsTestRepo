/*************************************************
 * Created by Efendi Hariyadi on 22/06/22, 1:42 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 22/06/22, 1:42 PM
 ************************************************/

package com.prateek.nearwe.api.models.posts

import java.io.Serializable

data class User(
    val emailAddress: String,
    val isAnonymous: Int,
    val name: String,
   val  userId: String
):Serializable