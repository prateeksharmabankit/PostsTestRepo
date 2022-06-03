/*************************************************
 * Created by Efendi Hariyadi on 6/18/20 11:20 PM
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 6/18/20 11:20 PM
 ************************************************/

package com.numesa.android.simpeldesa.api.requests

import com.numesa.android.simpeldesa.api.models.Comments.CommentsModel
import com.numesa.android.simpeldesa.api.models.posts.PostModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface PostsServices {
    @Headers("Accept: application/json")
    @GET("posts")
    suspend fun listUsers():Response< List<PostModel>>


    @GET("posts/{id}/comments")
    suspend fun getCommentsByPostId(@Path("id") id: Int,):Response<List<CommentsModel>>
}