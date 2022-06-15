package com.prateek.nearwe.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.prateek.nearwe.api.PostsServices
import com.prateek.nearwe.repository.FirestoreRepository
import com.prateek.nearwe.repository.PostsServerRepository
import org.koin.dsl.module
import retrofit2.Retrofit

val FirestoreModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }


}