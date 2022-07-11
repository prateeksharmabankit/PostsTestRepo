/*************************************************
 * Created by Efendi Hariyadi on 11/07/22, 11:15 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 11/07/22, 11:15 AM
 ************************************************/

package com.prateek.nearwe.api.models.chatrooms.chatcontent

data class ChatContentResponse(
    val code: Int,
    val error: Boolean,
    val message: String,
    val results: Results
)