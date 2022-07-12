package com.prateek.nearwe.repository

import com.prateek.nearwe.api.PostsServices
import com.prateek.nearwe.api.models.chatrooms.AddchatRoom.AddChatroomRequest
import com.prateek.nearwe.api.models.chatrooms.AddchatRoom.AddChatroomResponse
import com.prateek.nearwe.api.models.chatrooms.addchatcontent.AddChatcontentRequest
import com.prateek.nearwe.api.models.chatrooms.chatcontent.ChatContentResponse
import com.prateek.nearwe.api.models.chatrooms.chatroomresponses.ChatroomResponse
import com.prateek.nearwe.api.models.posts.AddPost.AddPostRequest
import com.prateek.nearwe.api.models.posts.PostLikes.PostLikesRequest
import com.prateek.nearwe.api.models.posts.postlikedstatus.PostLikedStatus
import com.prateek.nearwe.api.models.posts.postresponse.PostResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path

class ChatroomServerRepository(private val postsServices: PostsServices) {
    fun getMyChats(userId: Int?): Flow<ChatroomResponse> {

        val latestNews: Flow<ChatroomResponse> = flow {
            while (true) {
                val latestNews = postsServices.getMyChats(userId)
                emit(latestNews) // Emits the result of the request to the flow
                delay(9000) // Suspends the coroutine for some time
            }
        }
        return latestNews
    }

    suspend fun CreateChatRoom( addChatroomRequest: AddChatroomRequest) =postsServices.CreateChatRoom(addChatroomRequest)

    suspend fun AddChatConent( addChatroomRequest: AddChatcontentRequest)=postsServices.AddChatConent(addChatroomRequest)

    fun getMyChatContent(chatId: String?): Flow<ChatContentResponse> {

        val latestNews: Flow<ChatContentResponse> = flow {
            while (true) {
                val latestNews = postsServices.getMyChatContent(chatId)
                emit(latestNews) // Emits the result of the request to the flow
                delay(6000) // Suspends the coroutine for some time
            }
        }
        return latestNews
    }

}