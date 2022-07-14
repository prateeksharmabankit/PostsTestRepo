package com.prateek.nearwe.repository

import com.prateek.nearwe.api.models.User.UserModel

import com.prateek.nearwe.room.dao.UserDAO


class PostsRoomRepository(private val userDAO: UserDAO) {
    suspend fun checkisUserLoggedIn() = userDAO.getUsers();
    suspend fun AddUser(userModel: UserModel?) = userDAO.AddUser(userModel)
    suspend fun deleteUser() = userDAO.delete()
    suspend fun updateProfile(userModel: UserModel) = userDAO.updatePofile(userModel)
     fun checkisUserLoggedInFlow() = userDAO.getUsersFlow();
}