
package com.prateek.nearwe.repository

import com.prateek.nearwe.api.PostsServices

class PostsServerRepository(private val postsServices: PostsServices) {
    suspend fun repoGetListUsers() = postsServices.listUsers()
}