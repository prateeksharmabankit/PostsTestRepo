
package com.numesa.android.simpeldesa.repository

import com.numesa.android.simpeldesa.api.requests.PostsServices

class PostsServerRepository(private val postsServices: PostsServices) {
    suspend fun repoGetListUsers() = postsServices.listUsers()
}