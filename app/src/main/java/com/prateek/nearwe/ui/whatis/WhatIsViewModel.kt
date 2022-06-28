package com.prateek.nearwe.ui.whatis


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prateek.nearwe.api.models.posts.AddPostViewsResponse.AddPostViewsResponse
import com.prateek.nearwe.api.models.posts.AppPostLikesResponse.AddPostLikesResponse
import com.prateek.nearwe.api.models.posts.postresponse.PostResponse
import com.prateek.nearwe.application.MainApp
import com.prateek.nearwe.repository.PostsServerRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
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




    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }


    fun loadWhatIsPosts(UserId: Int?) {
        loading.postValue(true)
        viewModelScope.launch {
            // Trigger the flow and consume its elements using collect
            postsServerRepository.getFoo(UserId, MainApp.instance.Latitude, MainApp.instance.Longitude).catch { e ->
                onError(e.message.toString())

            }.onEach {
                postList.postValue(it)
                loading.postValue(false)
            }.collect()



        }

    }


}