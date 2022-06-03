
package com.numesa.android.simpeldesa.ui.posts

import androidx.lifecycle.*
import com.numesa.android.simpeldesa.api.models.posts.PostModel

import com.numesa.android.simpeldesa.repository.PostsRoomRepository
import com.numesa.android.simpeldesa.repository.PostsServerRepository
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException

class PostsViewModel(
    private val usersRepository: PostsServerRepository,
    private val postsPersistanceRepositoru: PostsRoomRepository

) : ViewModel() {


    val errorMessage = MutableLiveData<String>()

    val userList = MutableLiveData<List<PostModel>>()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun getAllComments() {
        job = CoroutineScope(Dispatchers.Main + exceptionHandler).launch {
            var response = postsPersistanceRepositoru.getPosts();
            withContext(Dispatchers.Main) {
                if (response!!.isEmpty()) {
                    try {
                        val result = usersRepository.repoGetListUsers()
                        addPosts(result.body())
                        userList.postValue(result.body())
                    } catch (throwable: Throwable) {
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
                } else {
                    userList.postValue(response)
                }
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

    val userFavourites = MutableLiveData<List<PostModel>>()
    fun addPosts(birdsEntity: List<PostModel>?) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                postsPersistanceRepositoru.AddPosts(birdsEntity)
            }
        }
    }


    init {
        getFavourites()
    }

    fun updateFavourite(usersResponse: PostModel) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                postsPersistanceRepositoru.setFavouriteStatus(usersResponse)
                getFavourites()
            }
        }
    }

    fun getFavourites() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                var usersResponse = postsPersistanceRepositoru.getFavouritePosts(true);
                userFavourites.postValue(usersResponse)


            }
        }

    }


}