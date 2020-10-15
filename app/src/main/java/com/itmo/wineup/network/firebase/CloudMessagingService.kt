package com.itmo.wineup.network.firebase

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class CloudMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d("FCM", "New token acquired: $token")
        // Send this to server if needed
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("FCM", "Received message from ${remoteMessage.from}")
        if (remoteMessage.data.isNotEmpty()) {
            Log.d("FCM", "Payload: ${remoteMessage.data}")
        }
        remoteMessage.notification?.let {
            val message = it.body
            val title = it.title
            Log.d("FCM", "$message, $title")
        }

        // Do stuff when app is in foreground
    }
}