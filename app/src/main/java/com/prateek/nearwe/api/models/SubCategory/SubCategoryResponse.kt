/*************************************************
 * Created by Efendi Hariyadi on 17/06/22, 7:28 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 17/06/22, 7:28 PM
 ************************************************/

package com.prateek.nearwe.api.models.SubCategory

data class SubCategoryResponse(
    val ErrorMessage: Any,
    val IsError: Boolean,
    val Message: Any,
    val Result: List<Result>
)