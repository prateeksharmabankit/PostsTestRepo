
package com.prateek.nearwe.repository

import com.prateek.nearwe.api.PostsServices
import okhttp3.MultipartBody


class CommentsServerRepository(private val postsServices: PostsServices) {
    suspend fun AddCommentImage(formFile: MultipartBody.Part )= postsServices.AddCommentImage(formFile)

}