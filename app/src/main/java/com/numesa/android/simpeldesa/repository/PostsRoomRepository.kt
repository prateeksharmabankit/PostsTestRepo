package com.numesa.android.simpeldesa.repository

import com.numesa.android.simpeldesa.api.models.posts.PostModel

import com.numesa.android.simpeldesa.room.dao.PostsDAO


class PostsRoomRepository(private val birdsDAO: PostsDAO) {

    suspend fun AddPosts(birdsEntity: List<PostModel>?) = birdsDAO.AddPosts(birdsEntity)
    suspend fun getPosts() = birdsDAO.getPosts()
    suspend fun setFavouriteStatus(usersResponse: PostModel) =
        birdsDAO.setFavouriteStatus(usersResponse.id, usersResponse.isFavourite)
    suspend fun getFavouritePosts(boolean: Boolean) =birdsDAO.getFavouritePosts(boolean)
}