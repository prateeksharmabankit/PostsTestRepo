package com.prateek.nearwe.ui.posts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prateek.nearwe.api.models.posts.AddPost.AddPostRequest
import com.prateek.nearwe.api.models.posts.AddPost.AddPostResponse
import com.prateek.nearwe.api.models.posts.AddPostViewsResponse.AddPostViewsResponse
import com.prateek.nearwe.api.models.posts.AppPostLikesResponse.AddPostLikesResponse
import com.prateek.nearwe.api.models.posts.PostLikes.PostLikesRequest
import com.prateek.nearwe.api.models.posts.PostResponse
import com.prateek.nearwe.repository.PostsRoomRepository
import com.prateek.nearwe.repository.PostsServerRepository
import kotlinx.coroutines.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException


class PostsViewModel(
    private val usersRepository: PostsServerRepository,
    private val postsPersistanceRepositoru: PostsRoomRepository

) : ViewModel() {
    val errorMessage = MutableLiveData<String>()

    val addPostViewResponse = MutableLiveData<AddPostViewsResponse>()
    val addPostLikesResponse = MutableLiveData<AddPostLikesResponse>()
    val addPostResponse = MutableLiveData<AddPostResponse>()
    val userList = MutableLiveData<PostResponse>()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun getAllPosts(userId: Int?, latitude: String, longitude: String) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val result = usersRepository.GetAllPosts(userId, latitude, longitude)
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

    fun AddPost(postRequest: AddPostRequest) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val result = usersRepository.AddPost(postRequest)
            withContext(Dispatchers.Main) {
                try {
                    loading.postValue(false)


                    addPostResponse.postValue(result.body())
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

    fun AddWhatsIsPost(file: File, postRequest: AddPostRequest) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            val reqFile = RequestBody.create(MediaType.parse("image/*"), file)
            val body = MultipartBody.Part.createFormData(
                "formFile",
                file.name, reqFile
            )




            var title= RequestBody.create(
               MediaType.parse("text/plain"),
               postRequest.Title
           );
            var IsAnonymous= RequestBody.create(
                MediaType.parse("text/plain"),
                postRequest.IsAnonymous.toString()
            );
            var UserId= RequestBody.create(
                MediaType.parse("text/plain"),
                postRequest.UserId.toString()
            );





            var Latitude= RequestBody.create(
                MediaType.parse("text/plain"),
                postRequest.Latitude.toString()
            );
            var Longitude= RequestBody.create(
                MediaType.parse("text/plain"),
                postRequest.Longitude.toString()
            );
            var PostType= RequestBody.create(
                MediaType.parse("text/plain"),
                postRequest.PostType.toString()
            );

            var PostSubCategories= RequestBody.create(
                MediaType.parse("text/plain"),
                postRequest.PostSubCategories.toString()
            );
            var image= RequestBody.create(
                MediaType.parse("text/plain"),
                ""
            );

            val result = usersRepository.AddWhatIsPost(body,title,IsAnonymous, UserId, Latitude, Longitude, PostType,image, PostSubCategories)
            withContext(Dispatchers.Main) {
                try {
                    loading.postValue(false)


                    addPostResponse.postValue(result.body())
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