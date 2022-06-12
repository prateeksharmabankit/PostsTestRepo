package com.prateek.nearwe.di

import com.prateek.nearwe.api.PostsServices
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single(createdAtStart = false) { get<Retrofit>().create(PostsServices::class.java) }
}