
package com.prateek.nearwe.ui.home

import androidx.lifecycle.*
import com.prateek.nearwe.api.models.SubCategory.SubCategoriesResponse
import com.prateek.nearwe.api.models.posts.AddPost.AddPostRequest
import com.prateek.nearwe.api.models.posts.AddPost.AddPostResponse
import com.prateek.nearwe.repository.PostsServerRepository

import com.prateek.nearwe.repository.SubCategoryRepository
import kotlinx.coroutines.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException

class HomeViewModel(
    private val subCategoryRepository: SubCategoryRepository,
    private val postsServerRepository: PostsServerRepository


) : ViewModel() {
    val errorMessage = MutableLiveData<String>()

    val addPostResponse = MutableLiveData<AddPostResponse>()

    val userList = MutableLiveData<SubCategoriesResponse>()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
       // onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun getSubcategoriesByCategoryId(categoryId:Int) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val result = subCategoryRepository.getSubcategoriesByCategoryId(categoryId)
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


    fun AddPost(postRequest: AddPostRequest) {

        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val result = postsServerRepository.AddPost(postRequest)
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


            var title = RequestBody.create(
                MediaType.parse("text/plain"),
                postRequest.Title
            );
            var IsAnonymous = RequestBody.create(
                MediaType.parse("text/plain"),
                postRequest.IsAnonymous.toString()
            );
            var UserId = RequestBody.create(
                MediaType.parse("text/plain"),
                postRequest.UserId.toString()
            );


            var Latitude = RequestBody.create(
                MediaType.parse("text/plain"),
                postRequest.Latitude.toString()
            );
            var Longitude = RequestBody.create(
                MediaType.parse("text/plain"),
                postRequest.Longitude.toString()
            );
            var PostType = RequestBody.create(
                MediaType.parse("text/plain"),
                postRequest.PostType.toString()
            );
            var image = RequestBody.create(
                MediaType.parse("text/plain"),
                ""
            );

            var PostSubCategories = RequestBody.create(
                MediaType.parse("text/plain"),
                postRequest.SubCategories.toString()
            );
            var CategoryId = RequestBody.create(
                MediaType.parse("text/plain"),
                postRequest.CategoryId.toString()
            );
            var CategoryName = RequestBody.create(
                MediaType.parse("text/plain"),
                postRequest.CategoryName.toString()
            );


            val result = postsServerRepository.AddWhatIsPost(
                body,
                title,
                IsAnonymous,
                UserId,
                Latitude,
                Longitude,
                PostType,
                image,
                PostSubCategories,
                CategoryId,
                CategoryName
            )
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