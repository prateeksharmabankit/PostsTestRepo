
package com.prateek.nearwe.repository

import com.prateek.nearwe.api.PostsServices
import com.prateek.nearwe.api.models.User.UserModel
import com.prateek.nearwe.api.models.login.profile.UpdateProfileRequest
import com.prateek.nearwe.api.models.login.profile.UpdateProfileResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body

class LoginServerRepository(private val postsServices: PostsServices) {
    suspend fun loginUser(userModel: UserModel) = postsServices.LoginUser(userModel)
    suspend fun updateToken(userId :Int?,Token:String) = postsServices.UpdateNotification(userId,Token)
    suspend fun AddImage(formFile: MultipartBody.Part )= postsServices.AddCommentImage(formFile)
    suspend fun UpdateProfile(updateProfileRequest: UpdateProfileRequest)=postsServices.UpdateProfile(updateProfileRequest)
}