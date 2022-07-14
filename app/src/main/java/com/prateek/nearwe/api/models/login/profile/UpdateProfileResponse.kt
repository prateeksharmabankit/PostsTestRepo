/*************************************************
 * Created by Efendi Hariyadi on 14/07/22, 4:15 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 14/07/22, 4:15 PM
 ************************************************/

package com.prateek.nearwe.api.models.login.profile

data class UpdateProfileResponse(
    val code: Int,
    val error: Boolean,
    val message: String,
    val results: Results
)