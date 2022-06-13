/*************************************************
 * Created by Efendi Hariyadi on 12/06/22, 2:24 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 12/06/22, 2:24 PM
 ************************************************/

package com.prateek.nearwe.api.models.posts

import java.io.Serializable

data class PostSubCategoryDetailsDto(
    val CategoryName: String,
    val PostId: Int,
    val PostMappingId: Int,
    val SubCategoryId: Int,
    val SubCategoryName: String
):Serializable