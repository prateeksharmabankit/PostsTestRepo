/*************************************************
 * Created by Efendi Hariyadi on 18/06/22, 11:42 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 18/06/22, 11:42 PM
 ************************************************/

package com.prateek.nearwe.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.prateek.nearwe.R
import com.prateek.nearwe.ui.login.LoginActivity

class MyFirebaseMessagingService : FirebaseMessagingService() {


    override fun onMessageReceived(remoteMessage: RemoteMessage) {


        val data = remoteMessage.data
        val postId = data["postId"]
        val title = data["title"]
        val desc = data["desc"]
        val type = data["type"]

        if (type != null&&postId!=null&&type.toInt()!=123251) {
            sendNotification(title.toString(),desc.toString(), postId.toInt())
        }
        else{
            if (type != null&&postId!=null&&type.toInt()==123251) {
                val imageUrl = data["imgUrl"]
                sendNotificationwithimage(title.toString(),desc.toString(), postId.toInt(),imageUrl.toString())
            }


        }

    }

    override fun onNewToken(token: String) {


    }


    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }



    private fun sendNotification(title:String,des:String, postId: Int) {
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("postId", postId.toInt());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT
        )


        val channelId = "default_notification_channel_id"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)


        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_baseline_trending_24)
            .setContentTitle(title)

            .setContentText(des)

            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }
    private fun sendNotificationwithimage(title:String,des:String, postId: Int,imgUrl:String) {
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("postId", postId.toInt());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val futureTarget = Glide.with(this)
            .asBitmap()
            .load(imgUrl)
            .submit()
        val bitmap = futureTarget.get()


        Glide.with(this).clear(futureTarget)
        val channelId = "default_notification_channel_id"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val bigPictureStyle = NotificationCompat.BigPictureStyle()
            .setBigContentTitle(title)
            .setSummaryText(des)
            .bigLargeIcon(bitmap)
            .bigPicture(bitmap)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_baseline_trending_24)
            .setContentTitle(title)
            .setLargeIcon(bitmap)
            .setContentText(des)
            .setStyle(bigPictureStyle)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }
}