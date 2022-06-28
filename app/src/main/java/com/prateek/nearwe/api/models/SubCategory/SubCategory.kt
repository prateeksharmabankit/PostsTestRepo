/*************************************************
 * Created by Efendi Hariyadi on 27/06/22, 11:43 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 27/06/22, 11:43 PM
 ************************************************/

package com.prateek.nearwe.api.models.SubCategory

data class SubCategory(
    val __v: Int,
    val _id: String,
    val categoryId: Int,
    val subCategoryId: Int,
    val subCategoryName: String,
    var isCHecked: Boolean,
)