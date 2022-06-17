package com.prateek.nearwe.repository

import com.prateek.nearwe.api.PostsServices
import com.prateek.nearwe.api.models.posts.PostLikes.PostLikesRequest

class SubCategoryRepository(private val postsServices: PostsServices) {
    suspend fun getSubcategoriesByCategoryId(CategoryId:Int) =
        postsServices.getSubcategoriesByCategoryId(CategoryId)








}