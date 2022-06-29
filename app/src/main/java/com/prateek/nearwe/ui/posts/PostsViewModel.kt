package com.prateek.nearwe.ui.posts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prateek.nearwe.api.models.posts.AddPostViewsResponse.AddPostViewsResponse
import com.prateek.nearwe.api.models.posts.AppPostLikesResponse.AddPostLikesResponse
import com.prateek.nearwe.api.models.posts.PostLikes.PostLikesRequest
import com.prateek.nearwe.api.models.posts.postlikedstatus.PostLikedStatus
import com.prateek.nearwe.api.models.posts.postresponse.PostResponse
import com.prateek.nearwe.application.MainApp
import com.prateek.nearwe.repository.PostsRoomRepository
import com.prateek.nearwe.repository.PostsServerRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import retrofit2.HttpException
import java.io.IOException


class PostsViewModel(
    private val usersRepository: PostsServerRepository,
    private val postsPersistanceRepositoru: PostsRoomRepository

) : ViewModel() {
    val errorMessage = MutableLiveData<String>()

    val addPostViewResponse = MutableLiveData<AddPostViewsResponse>()
    val addPostLikesResponse = MutableLiveData<AddPostLikesResponse>()
    val getPostLikesResponse = MutableLiveData<PostLikedStatus>()
    val postList = MutableLiveData<PostResponse>()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        //  onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun getAllPosts(UserId: Int?) {
        loading.postValue(true)
        viewModelScope.launch {
            usersRepository.GetAllPosts(
                UserId,
                MainApp.instance.Latitude,
                MainApp.instance.Longitude
            ).catch { e ->
                onError(e.message.toString())

            }.onEach {
                postList.postValue(it)
                loading.postValue(false)
            }.collect()
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


    fun AddPostViews(PostId: Int) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            var addPostResponse = usersRepository.AddPostViews(PostId)
            withContext(Dispatchers.Main) {
                try {
                    loading.postValue(false)

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

    fun LikeUnlikePost(request: PostLikesRequest) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            var addPostResponse = usersRepository.AddPostLikesUnLike(request)
            withContext(Dispatchers.Main) {
                try {
                    loading.postValue(false)

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

    fun GetPostLikes(postId: Int,userId:Int) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            var addPostResponse = usersRepository.GetPostsLikes(postId,userId)
            withContext(Dispatchers.Main) {
                try {
                    loading.postValue(false)

                    getPostLikesResponse.postValue(addPostResponse)


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