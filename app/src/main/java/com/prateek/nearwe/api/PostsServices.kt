/*************************************************
 * Created by Efendi Hariyadi on 11/06/22, 3:28 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 11/06/22, 3:28 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 6/18/20 11:20 PM
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 6/18/20 11:20 PM
 ************************************************/

package com.prateek.nearwe.api

import com.prateek.nearwe.api.models.Comments.CommentsModel
import com.prateek.nearwe.api.models.User.UserModel
import com.prateek.nearwe.api.models.login.LoginResponse
import com.prateek.nearwe.api.models.posts.PostResponse
import retrofit2.Response
import retrofit2.http.*

interface PostsServices {
    @Headers("Accept: application/json")
    @GET("Posts/GetAllPosts/{UserId}/{Latitude}/{Longitude}")
    suspend fun GetAllPosts(@Path("UserId") UserId: Int?,@Path("Latitude") Latitude: String,@Path("Longitude") Longitude: String):Response<PostResponse>


    @GET("posts/{id}/comments")
    suspend fun getCommentsByPostId(@Path("id") id: Int,):Response<List<CommentsModel>>

    @Headers("Accept: application/json")
    @POST("Register")
    suspend fun LoginUser(@Body userModel: UserModel):Response<LoginResponse>

}