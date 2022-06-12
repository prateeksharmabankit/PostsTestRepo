package com.prateek.nearwe.api.models.posts

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class PostModel(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("mainid") var mainid: Int? = null,
    @SerializedName("userId") var userId: Int? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("body") var body: String? = null,
    @SerializedName("isFavourite") var isFavourite: Boolean = false
)
