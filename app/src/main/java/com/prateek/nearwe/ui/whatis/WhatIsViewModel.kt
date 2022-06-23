package com.prateek.nearwe.ui.whatis


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prateek.nearwe.api.models.posts.AddPostViewsResponse.AddPostViewsResponse
import com.prateek.nearwe.api.models.posts.AppPostLikesResponse.AddPostLikesResponse
import com.prateek.nearwe.api.models.posts.PostResponse
import com.prateek.nearwe.repository.PostsServerRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class WhatIsViewModel(
    private val postsServerRepository: PostsServerRepository
) : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val addPostLikesResponse = MutableLiveData<AddPostLikesResponse>()
    val addPostViewResponse = MutableLiveData<AddPostViewsResponse>()
    val postList = MutableLiveData<PostResponse>()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        //onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()


    /*  fun getWhatIsPosts(userId:Int?,latitude:String,longitude:String) {
          loading.postValue(true)
          job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
              val result = postsServerRepository.GetAllWhatisPosts(userId,latitude,longitude)
              withContext(Dispatchers.Main) {
                  try {
                      loading.postValue(false)


                      postList.postValue(result.body())
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

      }*/

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }


    fun loadFoo(UserId: Int?, Latitude: String, Longitude: String) {
        viewModelScope.launch {
            // Trigger the flow and consume its elements using collect
            postsServerRepository.getFoo(UserId, Latitude, Longitude).onEach {
                postList.postValue(it)
            }.collect()



        }

    }


}