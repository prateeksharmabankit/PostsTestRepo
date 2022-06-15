package com.prateek.nearwe.ui.comments

import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.firestore.*
import com.google.gson.Gson
import com.prateek.nearwe.api.models.Comments.CommentRequest.CommentRequest
import com.prateek.nearwe.repository.FirestoreRepository
import com.prateek.nearwe.utils.Utils
import kotlinx.coroutines.*
import java.util.*


class CommentsViewModel(
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {
    val TAG = "FIRESTORE_VIEW_MODEL"
    val errorMessage = MutableLiveData<String>()
    var commentsModelList: MutableLiveData<List<CommentRequest>> = MutableLiveData()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()


    fun getSavedAddresses(PostId: Int) {

var commentList=ArrayList<CommentRequest>()
        job = CoroutineScope(Dispatchers.Main + exceptionHandler).launch {

            withContext(Dispatchers.Main) {


                firestoreRepository.firestoreDB.collection(PostId.toString())

                    .addSnapshotListener { snapshots, e ->
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
                    .set(commentRequest, SetOptions.merge())

            }
        }

    }
}
