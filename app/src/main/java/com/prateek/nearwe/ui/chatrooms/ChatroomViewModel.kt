package com.prateek.nearwe.ui.chatrooms

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prateek.nearwe.api.models.chatrooms.AddchatRoom.AddChatroomRequest
import com.prateek.nearwe.api.models.chatrooms.AddchatRoom.AddChatroomResponse
import com.prateek.nearwe.api.models.chatrooms.chatroomresponses.ChatroomResponse
import com.prateek.nearwe.api.models.posts.AddPostViewsResponse.AddPostViewsResponse
import com.prateek.nearwe.api.models.posts.AppPostLikesResponse.AddPostLikesResponse
import com.prateek.nearwe.api.models.posts.PostLikes.PostLikesRequest
import com.prateek.nearwe.api.models.posts.postlikedstatus.PostLikedStatus
import com.prateek.nearwe.api.models.posts.postresponse.PostResponse
import com.prateek.nearwe.application.MainApp
import com.prateek.nearwe.repository.ChatroomServerRepository
import com.prateek.nearwe.repository.PostsRoomRepository
import com.prateek.nearwe.repository.PostsServerRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import retrofit2.HttpException
import java.io.IOException


class ChatroomViewModel(
    private val chatroomServerRepository: ChatroomServerRepository

) : ViewModel() {
    val errorMessage = MutableLiveData<String>()


    val chatRoomList = MutableLiveData<ChatroomResponse>()
    val chatRoomCreateResponse = MutableLiveData<AddChatroomResponse>()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        //  onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun getMyChats(UserId: Int?) {
        loading.postValue(true)
        viewModelScope.launch {
            chatroomServerRepository.getMyChats(UserId).catch { e ->
                onError(e.message.toString())

            }.onEach {
                chatRoomList.postValue(it)
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


    fun CreateChatRoom(addChatroomRequest: AddChatroomRequest) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val addPostResponse = chatroomServerRepository.CreateChatRoom(addChatroomRequest)
            withContext(Dispatchers.Main) {
                try {
                    loading.postValue(false)

                    chatRoomCreateResponse.postValue(addPostResponse.body())


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