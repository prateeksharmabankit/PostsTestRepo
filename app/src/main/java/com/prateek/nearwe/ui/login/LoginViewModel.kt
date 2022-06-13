package com.prateek.nearwe.ui.login

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.*
import com.prateek.nearwe.api.models.User.UserModel
import com.prateek.nearwe.api.models.login.LoginResponse
import com.prateek.nearwe.repository.LoginServerRepository
import com.prateek.nearwe.repository.PostsRoomRepository
import com.prateek.nearwe.utils.Utils
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException
import java.util.*

class LoginViewModel(
    private val loginServerRepository: LoginServerRepository,
    private val postsPersistanceRepositoru: PostsRoomRepository

) : ViewModel() {


    val errorMessage = MutableLiveData<String>()
    val addressDetails = MutableLiveData<String>()

    val loginStatus = MutableLiveData<Boolean>()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    val loginResponse = MutableLiveData<LoginResponse>()

    val userDetails = MutableLiveData<UserModel>()
    fun checkLoginStatus() {
        job = CoroutineScope(Dispatchers.Main + exceptionHandler).launch {
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
        job = CoroutineScope(Dispatchers.Main + exceptionHandler).launch {
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
        job = CoroutineScope(Dispatchers.Main + exceptionHandler).launch {
            postsPersistanceRepositoru.AddUser(userModel);
        }

    }




    fun loginUser(userModel: UserModel) {
        loading.value = true
        job = CoroutineScope(Dispatchers.Main + exceptionHandler).launch {

            withContext(Dispatchers.Main) {
                try {
                    val result = loginServerRepository.loginUser(userModel)
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


    fun getAddressHeader(context: Context?, latitude:String, longitude:String) {
        job = CoroutineScope(Dispatchers.Main + exceptionHandler).launch {

            withContext(Dispatchers.Main) {
                try {
                    val result = Utils.CompanionClass.getAddressFromLatLng(context,latitude,longitude)
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


}