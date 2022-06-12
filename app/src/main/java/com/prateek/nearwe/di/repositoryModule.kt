

package com.prateek.nearwe.di

import com.prateek.nearwe.repository.CommentsServerRepository
import com.prateek.nearwe.repository.LoginServerRepository
import com.prateek.nearwe.repository.PostsRoomRepository
import com.prateek.nearwe.repository.PostsServerRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { PostsServerRepository(get()) }
    single { CommentsServerRepository(get()) }
    single { PostsRoomRepository(birdsDAO = get(), userDAO = get()) }
    single { LoginServerRepository(get()) }
}