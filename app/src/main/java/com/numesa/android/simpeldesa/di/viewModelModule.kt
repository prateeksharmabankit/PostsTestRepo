

package com.numesa.android.simpeldesa.di

import com.numesa.android.simpeldesa.ui.comments.CommentsViewModel
import com.numesa.android.simpeldesa.ui.posts.PostsViewModel

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {  PostsViewModel(usersRepository = get(), postsPersistanceRepositoru = get()) }
    viewModel {  CommentsViewModel(commentsServerRepository = get()) }
}

