

package com.numesa.android.simpeldesa.di

import androidx.room.Room
import com.numesa.android.simpeldesa.room.PostsDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val postDatabaseModule = module {

    single { Room.databaseBuilder(androidApplication(), PostsDatabase::class.java, "Posts").build() }
    single { get<PostsDatabase>().postsDAO() }
}




