/*************************************************
 * Created by Efendi Hariyadi on 11/07/22, 11:15 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 11/07/22, 11:15 AM
 ************************************************/

package com.prateek.nearwe.api.models.chatrooms.chatcontent

data class Data(
    val __v: Int,
    val _id: String,
    val chatId: String,
    val dateTimeStamp: String,
    val message: String,
    val reciever: Int,
    val recieveruser: Recieveruser,
    val sender: Int,
    val senderuser: Senderuser,
    val ago: String,
)