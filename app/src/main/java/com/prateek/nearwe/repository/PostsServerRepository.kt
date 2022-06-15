package com.prateek.nearwe.repository

import androidx.annotation.WorkerThread
import com.prateek.nearwe.api.PostsServices
import com.prateek.nearwe.api.models.posts.AddPostViewsResponse.AddPostViewsResponse
import com.prateek.nearwe.api.models.posts.PostLikes.PostLikesRequest
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Path

class PostsServerRepository(private val postsServices: PostsServices) {
    suspend fun GetAllPosts(UserId: Int?, Latitude: String, Longitude: String) =
        postsServices.GetAllPosts(UserId, Latitude, Longitude)

    suspend fun AddPostViews(PostId: Int) = postsServices.AddPostViews(PostId)

    suspend fun AddPostLikesUnLike(postLikesRequest: PostLikesRequest) =
        postsServices.AddPostLikesUnLike(postLikesRequest)





}