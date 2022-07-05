
package com.prateek.nearwe.ui.trending

import androidx.lifecycle.*
import com.prateek.nearwe.api.models.posts.AddPostViewsResponse.AddPostViewsResponse
import com.prateek.nearwe.api.models.posts.AppPostLikesResponse.AddPostLikesResponse
import com.prateek.nearwe.api.models.posts.postresponse.PostResponse
import com.prateek.nearwe.application.MainApp

import com.prateek.nearwe.repository.PostsServerRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import retrofit2.HttpException
import java.io.IOException

class TrendingViewModel(
    private val postsServerRepository: PostsServerRepository
) : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val userList = MutableLiveData<PostResponse>()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
      //  onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()


    fun getAllTrendingPosts(UserId: Int?) {
        loading.postValue(true)
        viewModelScope.launch {
            // Trigger the flow and consume its elements using collect
            postsServerRepository.GetAllTrendingPosts(UserId, MainApp.instance.Latitude, MainApp.instance.Longitude).catch { e ->
                onError(e.message.toString())

            }.onEach {
                userList.postValue(it)
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




}