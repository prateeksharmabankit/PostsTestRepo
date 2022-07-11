

package com.prateek.nearwe.di

import com.prateek.nearwe.repository.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModule = module {
    single { PostsServerRepository(get()) }
    single { CommentsServerRepository(get()) }
    single { PostsRoomRepository( userDAO= get()) }
    single { LoginServerRepository(get()) }
    single { FirestoreRepository(get(),get()) }
    single { SubCategoryRepository(postsServices =  get()) }
    single { ChatroomServerRepository(postsServices =  get()) }

}