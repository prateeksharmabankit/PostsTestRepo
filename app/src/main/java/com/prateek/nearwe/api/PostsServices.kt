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

import com.prateek.nearwe.api.models.SubCategory.SubCategoriesResponse
import com.prateek.nearwe.api.models.User.UserModel
import com.prateek.nearwe.api.models.login.LoginResponse
import com.prateek.nearwe.api.models.posts.AddPost.AddPostRequest
import com.prateek.nearwe.api.models.posts.AddPost.AddPostResponse
import com.prateek.nearwe.api.models.posts.AddPostViewsResponse.AddPostViewsResponse
import com.prateek.nearwe.api.models.posts.AppPostLikesResponse.AddPostLikesResponse
import com.prateek.nearwe.api.models.posts.PostLikes.PostLikesRequest
import com.prateek.nearwe.api.models.posts.postresponse.PostResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*


interface PostsServices {
    @Headers("Accept: application/json")
    @GET("api/Posts/GetAllPosts/{userId}/{latitude}/{longitude}")
    suspend fun GetAllPosts(
        @Path("userId") UserId: Int?,
        @Path("latitude") Latitude: String,
        @Path("longitude") Longitude: String
    ): PostResponse

    @GET("api/Posts/GetAllTrendingPosts/{userId}/{latitude}/{longitude}")
    suspend fun GetAllTrendingPosts(
        @Path("userId") UserId: Int?,
        @Path("latitude") Latitude: String,
        @Path("longitude") Longitude: String
    ): Response<PostResponse>

    @GET("api/Posts/GetAllWhatisPosts/{userId}/{latitude}/{longitude}")
    suspend fun GetAllWhatisPosts(
        @Path("userId") UserId: Int?,
        @Path("latitude") Latitude: String,
        @Path("longitude") Longitude: String
    ): Response<PostResponse>


    @Headers("Accept: application/json")
    @POST("api/user/post")
    suspend fun LoginUser(@Body userModel: UserModel): Response<LoginResponse>

    @GET("api/Posts/AddPostView/{PostId}")
    suspend fun AddPostViews(@Path("PostId") PostId: Int): Response<AddPostViewsResponse>


    @POST("/PostLikesMapping/LikeUnlikePost")
    suspend fun AddPostLikesUnLike(@Body postLikesRequest: PostLikesRequest): Response<AddPostLikesResponse>

    @GET("api/getSubCategories/{categoryId}")
    suspend fun getSubcategoriesByCategoryId(@Path("categoryId") categoryId: Int): Response<SubCategoriesResponse>


    @POST("/Posts/Add")
    suspend fun AddPost(@Body postModel: AddPostRequest): Response<AddPostResponse>

    @Multipart
    @POST("/Posts/AddWhatIsPost")
    suspend fun AddWhatIsPost(

        @Part formFile: MultipartBody.Part ,
        @Part("Title") Title: RequestBody?,
        @Part("IsAnonymous") IsAnonymous: RequestBody?,

        @Part("UserId") UserId: RequestBody?,
        @Part("Latitude") Latitude: RequestBody?,

        @Part("Longitude") Longitude: RequestBody?,
        @Part("PostType") PostType: RequestBody?,
        @Part("ImageUrl") ImageUrl: RequestBody?,

        @Part("SubCategories") SubCategories: RequestBody?,
        @Part("CategoryId") CategoryId: RequestBody?,
        @Part("CategoryName") CategoryName: RequestBody?


    ): Response<AddPostResponse>

    @GET("api/Posts/GetAllWhatisPosts/{UserId}/{Latitude}/{Longitude}")
    suspend fun GetAllWhatisPost(
        @Path("UserId") UserId: Int?,
        @Path("Latitude") Latitude: String,
        @Path("Longitude") Longitude: String
    ):PostResponse

    @Headers("Accept: application/json")
    @GET("/User/UpdateToken/{UserId}/{Token}")
    suspend fun UpdateNotification(
        @Path("UserId") UserId: Int?,
        @Path("Token") Latitude: String

    ): Long
}