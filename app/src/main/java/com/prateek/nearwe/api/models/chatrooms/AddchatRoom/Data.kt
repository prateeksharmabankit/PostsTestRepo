/*************************************************
 * Created by Efendi Hariyadi on 08/07/22, 11:04 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 08/07/22, 11:04 PM
 ************************************************/

package com.prateek.nearwe.api.models.chatrooms.AddchatRoom

data class Data(

    val _id: String,
    val postId: Int,
    val reciever: Int,
    val sender: Int
)