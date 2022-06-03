package com.numesa.android.simpeldesa.room.dao


import androidx.room.Dao
import androidx.room.Insert

import androidx.room.Query
import com.numesa.android.simpeldesa.api.models.posts.PostModel

@Dao
interface PostsDAO {
    @Insert
    suspend fun AddPosts(birdsEntity: List<PostModel>?)

    @Query("SELECT * FROM PostModel")
    suspend fun getPosts(): List<PostModel>

    @Query("SELECT * FROM PostModel where isFavourite= :isFavourite ")
    suspend fun getFavouritePosts(isFavourite: Boolean): List<PostModel>


    @Query("UPDATE PostModel SET isFavourite = :isFavourite WHERE id = :id")
    suspend fun setFavouriteStatus(id: Int?, isFavourite: Boolean?): Int


}