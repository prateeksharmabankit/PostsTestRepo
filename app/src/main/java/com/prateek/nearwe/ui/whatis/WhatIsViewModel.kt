
package com.prateek.nearwe.ui.whatis

import androidx.lifecycle.*
import com.prateek.nearwe.api.models.posts.AddPostViewsResponse.AddPostViewsResponse
import com.prateek.nearwe.api.models.posts.AppPostLikesResponse.AddPostLikesResponse
import com.prateek.nearwe.api.models.posts.PostResponse

import com.prateek.nearwe.repository.PostsServerRepository
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException

class WhatIsViewModel(
    private val postsServerRepository: PostsServerRepository
) : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val addPostLikesResponse = MutableLiveData<AddPostLikesResponse>()
    val addPostViewResponse = MutableLiveData<AddPostViewsResponse>()
    val userList = MutableLiveData<PostResponse>()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        //onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun getWhatIsPosts(userId:Int?,latitude:String,longitude:String) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val result = postsServerRepository.GetAllWhatisPosts(userId,latitude,longitude)
            withContext(Dispatchers.Main) {
                try {
                    loading.postValue(false)


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

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }






}