package com.prateek.nearwe.api.models.Comments

import com.google.gson.annotations.SerializedName

data class CommentsModel(
    @SerializedName("name") var name: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("body") var body: String? = null
)
