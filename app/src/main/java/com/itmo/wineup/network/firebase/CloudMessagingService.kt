package com.itmo.wineup.network.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.itmo.wineup.R

class CloudMessagingService : FirebaseMessagingService() {

    private var notificationId = 0
    private val defaultChannelId = "WineUpDefaultChannel"

    override fun onNewToken(token: String) {
        Log.d("FCM", "New token acquired: $token")
        // Send this to server if needed
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        createNotificationChannel()
        Log.d("FCM", "Received message from ${remoteMessage.from}")
        if (remoteMessage.data.isNotEmpty()) {
            Log.d("FCM", "Payload: ${remoteMessage.data}")
        }
        remoteMessage.notification?.let {
            val message = it.body
            val title = it.title
            Log.d("FCM", "$message, $title")
            val builder = NotificationCompat.Builder(this, defaultChannelId)
                .setSmallIcon(R.drawable.ic_like_red)
                .setContentTitle(it.title)
                .setContentText(it.body)
            with(NotificationManagerCompat.from(applicationContext)) {
                notify(notificationId++, builder.build())
            }
        }

        // Do stuff when app is in foreground
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(defaultChannelId, "WineUp", importance).apply {
                description = "Default channel for WineUp notifications"
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}