/*************************************************
 * Created by Efendi Hariyadi on 08/07/22, 4:27 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 08/07/22, 4:27 PM
 ************************************************/

package com.prateek.nearwe.api.models.chatrooms.chatroomresponses

data class ChatroomResponse(
    val code: Int,
    val error: Boolean,
    val message: String,
    val results: Results
)