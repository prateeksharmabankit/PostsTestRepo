
package com.numesa.android.simpeldesa.repository

import com.numesa.android.simpeldesa.api.requests.PostsServices


class CommentsServerRepository(private val postsServices: PostsServices) {
    suspend fun getCommentsByPostId(id :Int )= postsServices.getCommentsByPostId(id)

}