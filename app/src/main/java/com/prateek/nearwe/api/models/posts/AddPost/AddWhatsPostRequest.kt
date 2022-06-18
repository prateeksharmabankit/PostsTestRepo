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
import okhttp3.RequestBody
import retrofit2.http.Part


data class AddWhatsPostRequest(

    @SerializedName("Title") var Title: RequestBody? = null,
    @SerializedName("IsAnonymous") var IsAnonymous: RequestBody? = null,
    @SerializedName("Latitude") var Latitude: RequestBody? = null,
    @SerializedName("Longitude") var Longitude: RequestBody? = null,
    @SerializedName("PostType") var PostType: RequestBody? = null,
    @SerializedName("PostSubCategories") var PostSubCategories: RequestBody? = null,
    @SerializedName("UserId") var UserId: RequestBody? = null,
    @SerializedName("formFile")var formFile: RequestBody?=null


    )
