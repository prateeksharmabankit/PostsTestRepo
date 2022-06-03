
package com.numesa.android.simpeldesa.room
import androidx.room.Database
import androidx.room.RoomDatabase
import com.numesa.android.simpeldesa.api.models.posts.PostModel

import com.numesa.android.simpeldesa.room.dao.PostsDAO


@Database(entities = [PostModel::class], version = 2)
abstract class PostsDatabase : RoomDatabase() {

    abstract fun postsDAO() : PostsDAO
}