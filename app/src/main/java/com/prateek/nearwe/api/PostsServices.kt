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

import com.prateek.nearwe.api.models.Comments.CommentRequest.CommentRequest
import com.prateek.nearwe.api.models.SubCategory.SubCategoryResponse
import com.prateek.nearwe.api.models.User.UserModel
import com.prateek.nearwe.api.models.login.LoginResponse
import com.prateek.nearwe.api.models.posts.AddPost.AddPostRequest
import com.prateek.nearwe.api.models.posts.AddPost.AddPostResponse
import com.prateek.nearwe.api.models.posts.AddPostViewsResponse.AddPostViewsResponse
import com.prateek.nearwe.api.models.posts.AppPostLikesResponse.AddPostLikesResponse
import com.prateek.nearwe.api.models.posts.PostLikes.PostLikesRequest
import com.prateek.nearwe.api.models.posts.PostResponse
import retrofit2.Response
import retrofit2.http.*

interface PostsServices {
    @Headers("Accept: application/json")
    @GET("Posts/GetAllPosts/{UserId}/{Latitude}/{Longitude}")
    suspend fun GetAllPosts(@Path("UserId") UserId: Int?,@Path("Latitude") Latitude: String,@Path("Longitude") Longitude: String):Response<PostResponse>

    @GET("Posts/GetAllTrendingPosts/{UserId}/{Latitude}/{Longitude}")
    suspend fun GetAllTrendingPosts(@Path("UserId") UserId: Int?,@Path("Latitude") Latitude: String,@Path("Longitude") Longitude: String):Response<PostResponse>

    @GET("Posts/GetAllWhatisPosts/{UserId}/{Latitude}/{Longitude}")
    suspend fun GetAllWhatisPosts(@Path("UserId") UserId: Int?,@Path("Latitude") Latitude: String,@Path("Longitude") Longitude: String):Response<PostResponse>




    @Headers("Accept: application/json")
    @POST("Register")
    suspend fun LoginUser(@Body userModel: UserModel):Response<LoginResponse>

    @GET("/Posts/AddPostView/{PostId}")
    suspend fun AddPostViews(@Path("PostId") PostId: Int):Response<AddPostViewsResponse>


    @POST("/PostLikesMapping/Add")
    suspend fun AddPostLikesUnLike(@Body postLikesRequest: PostLikesRequest):Response<AddPostLikesResponse>

    @GET("NearWeSubCategory/GetKeyPairValues/{CategoryId}")
    suspend fun getSubcategoriesByCategoryId(@Path("CategoryId") CategoryId:Int):Response<SubCategoryResponse>


    @POST("/Posts/Add")
    suspend fun AddPost(@Body postModel: AddPostRequest):Response<AddPostResponse>


}