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

package com.prateek.nearwe.api.models.posts.AddPost

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class AddPostRequest(

    @SerializedName("Title") var Title: String? = null,
    @SerializedName("IsAnonymous") var IsAnonymous: Int? = null,
    @SerializedName("Latitude") var Latitude: String? = null,
    @SerializedName("Longitude") var Longitude: String? = null,
    @SerializedName("PostType") var PostType: Int? = null,
    @SerializedName("SubCategories") var SubCategories: String? = null,
    @SerializedName("UserId") var UserId: Int? = null,
    @SerializedName("CategoryName") var CategoryName: String? = null,
    @SerializedName("CategoryId") var CategoryId: Int? = null


    )
