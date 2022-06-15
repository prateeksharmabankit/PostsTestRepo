/*************************************************
 * Created by Efendi Hariyadi on 14/06/22, 8:53 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 14/06/22, 8:53 PM
 ************************************************/

package com.prateek.nearwe.repository

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.prateek.nearwe.api.models.Comments.CommentRequest.*
import java.util.*
import kotlin.collections.ArrayList

class FirestoreRepository(
    val firestoreDB: FirebaseFirestore,
    firebaseAuth: FirebaseAuth
) {


    val TAG = "FIREBASE_REPOSITORY"

    var user = firebaseAuth.currentUser


}