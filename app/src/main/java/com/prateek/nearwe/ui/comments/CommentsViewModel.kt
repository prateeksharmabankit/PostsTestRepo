package com.prateek.nearwe.ui.comments

import androidx.lifecycle.*
import com.prateek.nearwe.api.models.Comments.CommentsModel
import com.prateek.nearwe.repository.CommentsServerRepository
import kotlinx.coroutines.*

class CommentsViewModel(
    private val commentsServerRepository: CommentsServerRepository
) : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val commentsList = MutableLiveData<List<CommentsModel>>()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun getAllComments(id: Int) {
        job = CoroutineScope(Dispatchers.Main + exceptionHandler).launch {
            val response = commentsServerRepository.getCommentsByPostId(id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    commentsList.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
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

}
