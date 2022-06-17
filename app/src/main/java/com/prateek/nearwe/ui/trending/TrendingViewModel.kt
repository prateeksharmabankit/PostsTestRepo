
package com.prateek.nearwe.ui.trending

import androidx.lifecycle.*
import com.prateek.nearwe.api.models.posts.AddPostViewsResponse.AddPostViewsResponse
import com.prateek.nearwe.api.models.posts.AppPostLikesResponse.AddPostLikesResponse
import com.prateek.nearwe.api.models.posts.PostLikes.PostLikesRequest
import com.prateek.nearwe.api.models.posts.PostModel
import com.prateek.nearwe.api.models.posts.PostResponse

import com.prateek.nearwe.repository.PostsRoomRepository
import com.prateek.nearwe.repository.PostsServerRepository
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException

class TrendingViewModel(
    private val postsServerRepository: PostsServerRepository
) : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val addPostLikesResponse = MutableLiveData<AddPostLikesResponse>()
    val addPostViewResponse = MutableLiveData<AddPostViewsResponse>()
    val userList = MutableLiveData<PostResponse>()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun getAllTrendingPosts(userId:Int?,latitude:String,longitude:String) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.Main + exceptionHandler).launch {

            withContext(Dispatchers.Main) {
                try {
                    loading.postValue(false)
                    val result = postsServerRepository.GetAllTrendingPosts(userId,latitude,longitude)

                    userList.postValue(result.body())
                } catch (throwable: Throwable) {
                    loading.postValue(false)
                    when (throwable) {
                        is IOException -> {
                            onError("Network Error")
                        }
                        is HttpException -> {
                            val codeError = throwable.code()
                            val errorMessageResponse = throwable.message()
                            onError("Error $errorMessageResponse : $codeError")
                        }
                        else -> {
                            onError("UnKnown error")
                        }
                    }
                }
                loading.value = false
            }
        }

    }
    fun AddPostViews(PostId:Int) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.Main + exceptionHandler).launch {

            withContext(Dispatchers.Main) {
                try {
                    loading.postValue(false)
                    var  addPostResponse=    postsServerRepository.AddPostViews(PostId)
                    addPostViewResponse.postValue(addPostResponse.body())


                } catch (throwable: Throwable) {
                    loading.postValue(false)
                    when (throwable) {
                        is IOException -> {
                            onError("Network Error")
                        }
                        is HttpException -> {
                            val codeError = throwable.code()
                            val errorMessageResponse = throwable.message()
                            onError("Error $errorMessageResponse : $codeError")
                        }
                        else -> {
                            onError("UnKnown error")
                        }
                    }
                }
                loading.value = false
            }
        }

    }
    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }



    fun LikeUnlikePost(request: PostLikesRequest) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.Main + exceptionHandler).launch {

            withContext(Dispatchers.Main) {
                try {
                    loading.postValue(false)
                    var  addPostResponse=    postsServerRepository.AddPostLikesUnLike(request)
                    addPostLikesResponse.postValue(addPostResponse.body())


                } catch (throwable: Throwable) {
                    loading.postValue(false)
                    when (throwable) {
                        is IOException -> {
                            onError("Network Error")
                        }
                        is HttpException -> {
                            val codeError = throwable.code()
                            val errorMessageResponse = throwable.message()
                            onError("Error $errorMessageResponse : $codeError")
                        }
                        else -> {
                            onError("UnKnown error")
                        }
                    }
                }
                loading.value = false
            }
        }

    }


}