/*************************************************
 * Created by Efendi Hariyadi on 27/06/22, 11:16 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 27/06/22, 11:16 PM
 ************************************************/

package com.prateek.nearwe.api.models.login

data class Data(
    val __v: Int,
    val _id: String,
    val emailAddress: String,
    val name: String,
    val token: String,
    val userId: Int
)