
package com.prateek.nearwe.ui.home

import androidx.lifecycle.*
import com.prateek.nearwe.api.models.SubCategory.SubCategoriesResponse

import com.prateek.nearwe.repository.SubCategoryRepository
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException

class HomeViewModel(
    private val subCategoryRepository: SubCategoryRepository


) : ViewModel() {
    val errorMessage = MutableLiveData<String>()



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




}