/*************************************************
 * Created by Efendi Hariyadi on 03/06/22, 3:42 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 03/06/22, 3:42 PM
 ************************************************/

package com.numesa.android.simpeldesa.workmanager

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters


private const val TAG = "BlurWorker"
class FavouritesScheduler(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    override fun doWork(): Result {




        return try {
            Log.e(TAG, "successfull applying blur")
            Result.success()
        } catch (throwable: Throwable) {
            Log.e(TAG, "Error applying blur")
            Result.failure()
        }
    }
}
