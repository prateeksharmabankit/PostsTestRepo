

package com.prateek.nearwe.di

import com.prateek.nearwe.ui.comments.CommentsViewModel
import com.prateek.nearwe.ui.login.LoginViewModel
import com.prateek.nearwe.ui.posts.PostsViewModel
import io.reactivex.Single

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {  PostsViewModel(usersRepository = get(), postsPersistanceRepositoru = get()) }
    viewModel {  CommentsViewModel(firestoreRepository = get()) }
    viewModel {  LoginViewModel(loginServerRepository = get(), postsPersistanceRepositoru = get()) }


}

