/*************************************************
 * Created by Efendi Hariyadi on 08/07/22, 4:27 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 08/07/22, 4:27 PM
 ************************************************/

package com.prateek.nearwe.api.models.chatrooms.chatroomresponses

data class Recieveruser(
    val __v: Int,
    val _id: String,
    val emailAddress: String,
    val name: String,
    val token: String,
    val image: String,
    val userId: Int
)