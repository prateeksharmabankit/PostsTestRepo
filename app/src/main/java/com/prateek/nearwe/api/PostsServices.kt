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
import com.prateek.nearwe.api.models.posts.PostResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*
import java.util.concurrent.Flow


interface PostsServices {
    @Headers("Accept: application/json")
    @GET("Posts/GetAllPosts/{UserId}/{Latitude}/{Longitude}")
    suspend fun GetAllPosts(
        @Path("UserId") UserId: Int?,
        @Path("Latitude") Latitude: String,
        @Path("Longitude") Longitude: String
    ): Response<PostResponse>

    @GET("Posts/GetAllTrendingPosts/{UserId}/{Latitude}/{Longitude}")
    suspend fun GetAllTrendingPosts(
        @Path("UserId") UserId: Int?,
        @Path("Latitude") Latitude: String,
        @Path("Longitude") Longitude: String
    ): Response<PostResponse>

    @GET("Posts/GetAllWhatisPosts/{UserId}/{Latitude}/{Longitude}")
    suspend fun GetAllWhatisPosts(
        @Path("UserId") UserId: Int?,
        @Path("Latitude") Latitude: String,
        @Path("Longitude") Longitude: String
    ): Response<PostResponse>


    @Headers("Accept: application/json")
    @POST("Register")
    suspend fun LoginUser(@Body userModel: UserModel): Response<LoginResponse>

    @GET("/Posts/AddPostView/{PostId}")
    suspend fun AddPostViews(@Path("PostId") PostId: Int): Response<AddPostViewsResponse>


    @POST("/PostLikesMapping/LikeUnlikePost")
    suspend fun AddPostLikesUnLike(@Body postLikesRequest: PostLikesRequest): Response<AddPostLikesResponse>

    @GET("NearWeSubCategory/GetKeyPairValues/{CategoryId}")
    suspend fun getSubcategoriesByCategoryId(@Path("CategoryId") CategoryId: Int): Response<SubCategoriesResponse>


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

        @Part("PostSubCategories") PostSubCategories: RequestBody?
    ): Response<AddPostResponse>

    @GET("Posts/GetAllWhatisPosts/{UserId}/{Latitude}/{Longitude}")
    suspend fun GetAllWhatisPost(
        @Path("UserId") UserId: Int?,
        @Path("Latitude") Latitude: String,
        @Path("Longitude") Longitude: String
    ):PostResponse
}