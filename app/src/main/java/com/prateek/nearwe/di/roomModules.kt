

package com.prateek.nearwe.di

import androidx.room.Room
import com.prateek.nearwe.room.PostsDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val postDatabaseModule = module {

    single { Room.databaseBuilder(androidApplication(), PostsDatabase::class.java, "Posts").build() }
    /*single { get<PostsDatabase>().postsDAO() }*/
    single { get<PostsDatabase>().userDAO() }
}




