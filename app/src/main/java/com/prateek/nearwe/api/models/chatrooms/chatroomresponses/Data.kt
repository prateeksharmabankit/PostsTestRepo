/*************************************************
 * Created by Efendi Hariyadi on 08/07/22, 4:27 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 08/07/22, 4:27 PM
 ************************************************/

package com.prateek.nearwe.api.models.chatrooms.chatroomresponses

data class Data(
    val __v: Int,
    val _id: String,
    val chatcontent: Chatcontent,
    val collectionId: Int,
    val postId: Int,
    val reciever: Int,
    val recieveruser: Recieveruser,
    val sender: Int,
    val senderuser: Senderuser


)