package com.prateek.nearwe.repository

import com.prateek.nearwe.api.models.User.UserModel

import com.prateek.nearwe.room.dao.UserDAO


class PostsRoomRepository(private val userDAO: UserDAO) {

   /* suspend fun AddPosts(birdsEntity: List<PostResponse>?) = birdsDAO.AddPosts(birdsEntity)
    suspend fun getPosts() = birdsDAO.getPosts()
   *//* suspend fun setFavouriteStatus(usersResponse: PostResponse) =
        birdsDAO.setFavouriteStatus(usersResponse.id, usersResponse.isFavourite)*//*
    suspend fun getFavouritePosts(boolean: Boolean) =birdsDAO.getFavouritePosts(boolean)*/
    suspend fun checkisUserLoggedIn()=userDAO.getUsers();
   suspend fun AddUser(userModel: UserModel?)=userDAO.AddUser(userModel)
}