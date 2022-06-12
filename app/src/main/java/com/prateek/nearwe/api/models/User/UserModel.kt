/*************************************************
 * Created by Efendi Hariyadi on 11/06/22, 2:19 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 11/06/22, 2:18 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 11/06/22, 2:17 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 11/06/22, 2:17 PM
 ************************************************/

package com.prateek.nearwe.api.models.User

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class UserModel(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id") var Id: Int? = null,
    @SerializedName("UserId") var UserId: Int? = null,
    @SerializedName("EmailAddress") var EmailAddress: String? = null,
    @SerializedName("Name") var Name: String? = null,
    @SerializedName("Latitude") var Latitude: String? = null,
    @SerializedName("Longitude") var Longitude: String? = null,


    )
