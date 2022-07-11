/*************************************************
 * Created by Efendi Hariyadi on 11/06/22, 2:19 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 11/06/22, 2:18 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 11/06/22, 2:17 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 11/06/22, 2:17 PM
 ************************************************/

package com.prateek.nearwe.api.models.chatrooms.AddchatRoom

import com.google.gson.annotations.SerializedName


data class AddChatroomRequest(

    @SerializedName("sender") var Sender: Int? = null,
    @SerializedName("reciever") var Reciever: Int? = null,
    @SerializedName("postId") var PostId: Int? = null,
    )
