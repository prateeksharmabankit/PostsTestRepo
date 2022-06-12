
package com.prateek.nearwe.repository

import com.prateek.nearwe.api.PostsServices
import com.prateek.nearwe.api.models.User.UserModel

class LoginServerRepository(private val postsServices: PostsServices) {
    suspend fun loginUser(userModel: UserModel) = postsServices.LoginUser(userModel)
}