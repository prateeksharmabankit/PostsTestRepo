/*************************************************
 * Created by Efendi Hariyadi on 22/06/22, 3:10 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 22/06/22, 3:10 PM
 ************************************************/

package com.prateek.nearwe.api.models.SubCategory

data class SubCategoriesResponse(
    val errorMessage: String,
    val isError: Boolean,
    val message: String,
    val result: List<ResultList>
)