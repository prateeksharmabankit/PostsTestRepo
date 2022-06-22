/*************************************************
 * Created by Efendi Hariyadi on 22/06/22, 2:48 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 22/06/22, 2:48 PM
 ************************************************/

package com.prateek.nearwe.api.models.login

import com.prateek.nearwe.api.models.User.UserModel

data class LoginResponse(
    val errorMessage: String,
    val isError: Boolean,
    val message: String,
    val result: UserModel
)