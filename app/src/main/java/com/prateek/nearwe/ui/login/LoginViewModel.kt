package com.prateek.nearwe.ui.login

import android.content.Context
import androidx.lifecycle.*
import com.prateek.nearwe.api.models.Comments.CommentRequest.commentimage.CommentImageUploadResponse
import com.prateek.nearwe.api.models.User.UserModel
import com.prateek.nearwe.api.models.login.LoginResponse
import com.prateek.nearwe.api.models.login.profile.UpdateProfileRequest
import com.prateek.nearwe.api.models.login.profile.UpdateProfileResponse
import com.prateek.nearwe.application.MainApp
import com.prateek.nearwe.repository.CommentsServerRepository
import com.prateek.nearwe.repository.LoginServerRepository
import com.prateek.nearwe.repository.PostsRoomRepository
import com.prateek.nearwe.utils.Utils
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import java.util.*

class LoginViewModel(
    private val loginServerRepository: LoginServerRepository,
    private val postsPersistanceRepositoru: PostsRoomRepository,


) : ViewModel() {


    val errorMessage = MutableLiveData<String>()
    val addressDetails = MutableLiveData<String>()
    val imageUploadResponse = MutableLiveData<CommentImageUploadResponse>()
    val loginStatus = MutableLiveData<Boolean>()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    val loginResponse = MutableLiveData<LoginResponse>()
    val updateProfileResponse = MutableLiveData<UpdateProfileResponse>()

    val userDetails = MutableLiveData<UserModel>()
    fun checkLoginStatus() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            var response = postsPersistanceRepositoru.checkisUserLoggedIn();
            withContext(Dispatchers.Main) {
                if (response.isNotEmpty()) {
                    try {


                        loginStatus.postValue(true)
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
                    loginStatus.postValue(false)
                }
            }
        }

    }

    fun getLoggedInUser() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            var response = postsPersistanceRepositoru.checkisUserLoggedIn();
            withContext(Dispatchers.Main) {
                try {


                    userDetails.postValue(response.get(0))
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
            }
        }

    }

    fun saveUser(userModel: UserModel) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            postsPersistanceRepositoru.AddUser(userModel);
        }

    }


    fun loginUser(userModel: UserModel) {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val result = loginServerRepository.loginUser(userModel)
            withContext(Dispatchers.Main) {
                try {

                    loginResponse.postValue(result.body())
                    loading.value = false
                } catch (throwable: Throwable) {
                    loading.value = false
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
            }
        }

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


    fun getAddressHeader(context: Context?) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val result = Utils.CompanionClass.getAddressFromLatLng(context, MainApp.instance.Latitude, MainApp.instance.Longitude)
            withContext(Dispatchers.Main) {
                try {

                    addressDetails.postValue(result)
                    loading.value = false
                } catch (throwable: Throwable) {
                    loading.value = false
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
            }
        }

    }
    fun AddImage(file: File) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            val reqFile = RequestBody.create(MediaType.parse("image/*"), file)
            val body = MultipartBody.Part.createFormData("file", file.name, reqFile)
            val result = loginServerRepository.AddImage(body)
            withContext(Dispatchers.Main) {
                try {
                    loading.postValue(false)


                    imageUploadResponse.postValue(result.body())
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
    fun UpdateProfile(updateProfileRequest: UpdateProfileRequest) {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val result = loginServerRepository.UpdateProfile(updateProfileRequest)
            withContext(Dispatchers.Main) {
                try {

                    updateProfileResponse.postValue(result.body())
                    loading.value = false
                } catch (throwable: Throwable) {
                    loading.value = false
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
            }
        }

    }
    fun updateUserProfile(userModel: UserModel) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            postsPersistanceRepositoru.updateProfile(userModel);
        }

    }
    fun fullSchedule(): Flow<List<UserModel>> = postsPersistanceRepositoru.checkisUserLoggedInFlow()
    fun checkLoggedInUserFlow() {
        loading.postValue(true)
        viewModelScope.launch {
            fullSchedule().catch { e ->
                onError(e.message.toString())

            }.onEach {
                userDetails.postValue(it[0])
                loading.postValue(false)
            }.collect()
        }

    }


}