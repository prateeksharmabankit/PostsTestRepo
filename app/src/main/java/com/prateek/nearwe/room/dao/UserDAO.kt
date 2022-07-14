package com.prateek.nearwe.room.dao


import androidx.room.Dao
import androidx.room.Insert

import androidx.room.Query
import androidx.room.Update
import com.prateek.nearwe.api.models.User.UserModel
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {
    @Insert
    suspend fun AddUser(userModel: UserModel?)

    @Query("SELECT * FROM UserModel")
    suspend fun getUsers(): List<UserModel>

    @Query("DELETE FROM UserModel")
    fun delete()

    @Update
    fun updatePofile(user: UserModel)

    @Query("SELECT * FROM UserModel")
     fun getUsersFlow(): Flow<List<UserModel>>

}