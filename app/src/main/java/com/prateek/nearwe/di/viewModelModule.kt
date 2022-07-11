

package com.prateek.nearwe.di

import com.prateek.nearwe.ui.chatrooms.ChatroomViewModel
import com.prateek.nearwe.ui.comments.CommentsViewModel
import com.prateek.nearwe.ui.directchat.DirectChatViewModel
import com.prateek.nearwe.ui.home.HomeViewModel
import com.prateek.nearwe.ui.login.LoginViewModel
import com.prateek.nearwe.ui.posts.PostsViewModel
import com.prateek.nearwe.ui.trending.TrendingViewModel
import com.prateek.nearwe.ui.whatis.WhatIsViewModel

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {  PostsViewModel(usersRepository = get(),  get()) }
    viewModel {  CommentsViewModel(firestoreRepository = get(),get()) }
    viewModel {  ChatroomViewModel( get()) }
    viewModel {  LoginViewModel(loginServerRepository = get(), postsPersistanceRepositoru = get()) }
    viewModel {  TrendingViewModel(postsServerRepository = get()) }
    viewModel {  WhatIsViewModel(postsServerRepository = get()) }
    viewModel {  DirectChatViewModel(get()) }

    viewModel {  HomeViewModel(subCategoryRepository = get(),postsServerRepository=get(),loginServerRepository=get()) }

}

