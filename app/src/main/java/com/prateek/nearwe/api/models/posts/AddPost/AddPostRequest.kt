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

    @SerializedName("title") var Title: String? = null,
    @SerializedName("isAnonymous") var IsAnonymous: Int? = null,
    @SerializedName("latitude") var Latitude: String? = null,
    @SerializedName("longitude") var Longitude: String? = null,
    @SerializedName("postType") var PostType: Int? = null,
    @SerializedName("subCategories") var SubCategories: String? = null,
    @SerializedName("userId") var UserId: Int? = null,
    @SerializedName("categoryName") var CategoryName: String? = null,
    @SerializedName("categoryId") var CategoryId: Int? = null,
    @SerializedName("imageUrl") var imageUrl: String? = null,



    )
