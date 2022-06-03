package com.numesa.android.simpeldesa.di

import com.numesa.android.simpeldesa.api.requests.PostsServices
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single(createdAtStart = false) { get<Retrofit>().create(PostsServices::class.java) }
}