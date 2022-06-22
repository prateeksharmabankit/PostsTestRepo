package com.prateek.nearwe.room.dao


import androidx.room.Dao
import androidx.room.Insert

import androidx.room.Query
import com.prateek.nearwe.api.models.posts.PostResponse

@Dao
interface PostsDAO {
    @Insert
    suspend fun AddPosts(birdsEntity: List<PostResponse>?)

    @Query("SELECT * FROM PostModel")
    suspend fun getPosts(): List<PostResponse>

    @Query("SELECT * FROM PostModel where isFavourite= :isFavourite ")
    suspend fun getFavouritePosts(isFavourite: Boolean): List<PostResponse>


    @Query("UPDATE PostModel SET isFavourite = :isFavourite WHERE id = :id")
    suspend fun setFavouriteStatus(id: Int?, isFavourite: Boolean?): Int


}