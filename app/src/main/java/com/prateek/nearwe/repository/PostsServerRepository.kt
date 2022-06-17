package com.prateek.nearwe.repository

import com.prateek.nearwe.api.PostsServices
import com.prateek.nearwe.api.models.posts.PostLikes.PostLikesRequest

class PostsServerRepository(private val postsServices: PostsServices) {
    suspend fun GetAllPosts(UserId: Int?, Latitude: String, Longitude: String) =
        postsServices.GetAllPosts(UserId, Latitude, Longitude)


    suspend fun GetAllTrendingPosts(UserId: Int?, Latitude: String, Longitude: String) =
        postsServices.GetAllTrendingPosts(UserId, Latitude, Longitude)

    suspend fun GetAllWhatisPosts(UserId: Int?, Latitude: String, Longitude: String) =
        postsServices.GetAllWhatisPosts(UserId, Latitude, Longitude)

    suspend fun AddPostViews(PostId: Int) = postsServices.AddPostViews(PostId)

    suspend fun AddPostLikesUnLike(postLikesRequest: PostLikesRequest) =
        postsServices.AddPostLikesUnLike(postLikesRequest)





}