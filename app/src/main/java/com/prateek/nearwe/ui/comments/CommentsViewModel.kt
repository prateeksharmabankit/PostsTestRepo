package com.prateek.nearwe.ui.comments

import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.firestore.*
import com.google.gson.Gson
import com.prateek.nearwe.api.models.Comments.CommentRequest.CommentRequest
import com.prateek.nearwe.api.models.Comments.CommentRequest.commentimage.CommentImageUploadResponse
import com.prateek.nearwe.api.models.posts.AddPost.AddPostRequest
import com.prateek.nearwe.repository.CommentsServerRepository
import com.prateek.nearwe.repository.FirestoreRepository
import com.prateek.nearwe.repository.PostsServerRepository
import com.prateek.nearwe.utils.Utils
import kotlinx.coroutines.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import java.util.*


class CommentsViewModel(
    private val firestoreRepository: FirestoreRepository,
    private val commentsServerRepository: CommentsServerRepository
) : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    var commentsModelList: MutableLiveData<List<CommentRequest>> = MutableLiveData()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val commentImageUploadResponse = MutableLiveData<CommentImageUploadResponse>()

    val loading = MutableLiveData<Boolean>()


    fun getSavedAddresses(PostId: Int) {

        var commentList = ArrayList<CommentRequest>()
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            var scan =
                firestoreRepository.firestoreDB.collection(PostId.toString()).orderBy("dateTime")


            withContext(Dispatchers.Main) {

                scan.addSnapshotListener { snapshots, e ->
                    commentList.clear()
                    if (e != null) {

                        return@addSnapshotListener
                    }


                    for (dc in snapshots!!) {
                        var gson = Gson()
                        val json = Gson().toJson(dc.data)
                        var data = gson?.fromJson(json, CommentRequest::class.java)
                        commentList.add(data)


                    }
                    commentsModelList.postValue(commentList)


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


    fun addPostGroup(commentRequest: CommentRequest) {
        job = CoroutineScope(Dispatchers.Main + exceptionHandler).launch {

            withContext(Dispatchers.Main) {

                commentRequest.PostId = commentRequest.PostId

                firestoreRepository.firestoreDB.collection(commentRequest.PostId.toString())
                    .document(Utils.CompanionClass.getRandomString(20))
                    .set(commentRequest)

            }
        }

    }

    fun AddCommentImage(file: File) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            val reqFile = RequestBody.create(MediaType.parse("image/*"), file)
            val body = MultipartBody.Part.createFormData("file", file.name, reqFile)
            val result = commentsServerRepository.AddCommentImage(body)
            withContext(Dispatchers.Main) {
                try {
                    loading.postValue(false)


                    commentImageUploadResponse.postValue(result.body())
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
