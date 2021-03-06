/*************************************************
 * Created by Efendi Hariyadi on 27/06/22, 11:16 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 27/06/22, 11:16 PM
 ************************************************/

package com.prateek.nearwe.api.models.login

data class LoginResponse(
    val code: Int,
    val error: Boolean,
    val message: String,
    val results: Results
)