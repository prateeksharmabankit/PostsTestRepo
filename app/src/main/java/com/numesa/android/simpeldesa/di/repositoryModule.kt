

package com.numesa.android.simpeldesa.di

import com.numesa.android.simpeldesa.repository.CommentsServerRepository
import com.numesa.android.simpeldesa.repository.PostsRoomRepository
import com.numesa.android.simpeldesa.repository.PostsServerRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { PostsServerRepository(get()) }
    single { CommentsServerRepository(get()) }
    single { PostsRoomRepository(birdsDAO = get()) }
}