package com.prateek.nearwe.repository

import com.prateek.nearwe.api.PostsServices
import com.prateek.nearwe.api.models.posts.AddPost.AddPostRequest
import com.prateek.nearwe.api.models.posts.AddPost.AddPostResponse
import com.prateek.nearwe.api.models.posts.PostLikes.PostLikesRequest
import com.prateek.nearwe.api.models.posts.PostResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import java.io.File

class PostsServerRepository(private val postsServices: PostsServices) {
    fun GetAllPosts(UserId: Int?, Latitude: String, Longitude: String): Flow<PostResponse> {

        val latestNews: Flow<PostResponse> = flow {
            while (true) {
                val latestNews = postsServices.GetAllPosts(UserId, Latitude, Longitude)
                emit(latestNews) // Emits the result of the request to the flow
                delay(5000) // Suspends the coroutine for some time
            }
        }
        return latestNews
    }
    suspend fun GetAllTrendingPosts(UserId: Int?, Latitude: String, Longitude: String) =
        postsServices.GetAllTrendingPosts(UserId, Latitude, Longitude)

    suspend fun GetAllWhatisPosts(UserId: Int?, Latitude: String, Longitude: String) =
        postsServices.GetAllWhatisPosts(UserId, Latitude, Longitude)

    suspend fun AddPostViews(PostId: Int) = postsServices.AddPostViews(PostId)

    suspend fun AddPostLikesUnLike(postLikesRequest: PostLikesRequest) =
        postsServices.AddPostLikesUnLike(postLikesRequest)

    suspend fun AddPost(postModel: AddPostRequest) = postsServices.AddPost(postModel)

    suspend fun AddWhatIsPost(
        formFile: MultipartBody.Part,
        Title: RequestBody,
        IsAnonymous: RequestBody,
        UserId: RequestBody,
        Latitude: RequestBody,
        Longitude: RequestBody,
        PostType: RequestBody,
        ImageUrl: RequestBody,
        PostSubCategories: RequestBody,
        CategoryId:RequestBody,
        CategoryName:RequestBody
    ) = postsServices.AddWhatIsPost(
        formFile,
        Title,
        IsAnonymous,
        UserId,
        Latitude,
        Longitude,
        PostType,
        ImageUrl,
        PostSubCategories,
        CategoryId,
        CategoryName
    )


    fun getFoo(UserId: Int?, Latitude: String, Longitude: String): Flow<PostResponse> {

        val latestNews: Flow<PostResponse> = flow {
            while (true) {
                val latestNews = postsServices.GetAllWhatisPost(UserId, Latitude, Longitude)
                emit(latestNews) // Emits the result of the request to the flow
                delay(5000) // Suspends the coroutine for some time
            }
        }
        return latestNews
    }


}