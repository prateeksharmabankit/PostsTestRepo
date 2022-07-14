/*************************************************
 * Created by Efendi Hariyadi on 14/06/22, 11:48 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 14/06/22, 11:48 AM
 ************************************************/

package com.prateek.nearwe.api.models.login.profile

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest(
    @SerializedName("userId") var UserId: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("image") var image: String? = null,
)