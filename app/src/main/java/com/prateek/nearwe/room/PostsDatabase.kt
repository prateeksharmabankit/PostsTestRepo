
package com.prateek.nearwe.room
import androidx.room.Database
import androidx.room.RoomDatabase
import com.prateek.nearwe.api.models.User.UserModel
import com.prateek.nearwe.api.models.posts.PostModel

import com.prateek.nearwe.room.dao.PostsDAO
import com.prateek.nearwe.room.dao.UserDAO


@Database(entities = [PostModel::class, UserModel::class], version = 2)
abstract class PostsDatabase : RoomDatabase() {

    abstract fun postsDAO() : PostsDAO
    abstract fun userDAO() : UserDAO
}