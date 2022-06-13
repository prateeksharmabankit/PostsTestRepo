package com.prateek.nearwe.repository

import com.prateek.nearwe.api.PostsServices
import retrofit2.http.Path

class PostsServerRepository(private val postsServices: PostsServices) {
    suspend fun GetAllPosts(UserId: Int?, Latitude: String, Longitude: String) =
        postsServices.GetAllPosts(UserId, Latitude, Longitude)

}