/*************************************************
 * Created by Efendi Hariyadi on 22/06/22, 1:42 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 22/06/22, 1:42 PM
 ************************************************/

package com.prateek.nearwe.api.models.posts

import java.io.Serializable

data class PostSubCategoryDetailsDtoX(
    val categoryId: Int,
    val subCategoryId: Int,
    val subCategoryName: String
):Serializable