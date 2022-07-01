package com.prateek.nearwe.room.dao


import androidx.room.Dao
import androidx.room.Insert

import androidx.room.Query
import com.prateek.nearwe.api.models.User.UserModel

@Dao
interface UserDAO {
    @Insert
    suspend fun AddUser(userModel: UserModel?)

    @Query("SELECT * FROM UserModel")
    suspend fun getUsers(): List<UserModel>

    @Query("DELETE FROM UserModel")
    fun delete()
}