

package com.prateek.nearwe.di

import com.prateek.nearwe.repository.*
import org.koin.dsl.module

val repositoryModule = module {
    single { PostsServerRepository(get()) }
    single { CommentsServerRepository(get()) }
    single { PostsRoomRepository(birdsDAO = get(), userDAO = get()) }
    single { LoginServerRepository(get()) }
    single { FirestoreRepository(get(),get()) }
}