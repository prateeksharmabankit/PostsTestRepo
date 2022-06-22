/*************************************************
 * Created by Efendi Hariyadi on 22/06/22, 3:10 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 22/06/22, 3:10 PM
 ************************************************/

package com.prateek.nearwe.api.models.SubCategory

data class ResultList(
    val categoryId: Int,
    val subCategoryId: Int,
    val subCategoryName: String,
    var isCHecked:Boolean
)