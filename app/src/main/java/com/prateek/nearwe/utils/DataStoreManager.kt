/*************************************************
 * Created by Efendi Hariyadi on 07/07/22, 4:04 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 07/07/22, 4:04 PM
 ************************************************/

package com.prateek.nearwe.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class DataStoreManager(private val context: Context) {

//TODO here false is light, true is dark

    suspend fun setTheme(type: Boolean){
        context.dataStore.edit { preferences ->
            preferences[dark_mode] = type
        }
    }

    val getTheme : Flow<Boolean>
        get() = context.dataStore.data.map { preferences ->
            preferences[dark_mode] ?: false
        }

    companion object {
        private const val DATASTORE_NAME = "nearmedb"

        private val dark_mode = booleanPreferencesKey("dark_mode");

        private val Context.dataStore by preferencesDataStore(
            name = DATASTORE_NAME
        )
    }
}