
package com.prateek.nearwe.room
import androidx.room.Database
import androidx.room.RoomDatabase
import com.prateek.nearwe.api.models.User.UserModel

import com.prateek.nearwe.room.dao.UserDAO


@Database(entities = [/*PostResponse::class,*/ UserModel::class], version = 2)
abstract class PostsDatabase : RoomDatabase() {

  /*  abstract fun postsDAO() : PostsDAO*/
    abstract fun userDAO() : UserDAO
}