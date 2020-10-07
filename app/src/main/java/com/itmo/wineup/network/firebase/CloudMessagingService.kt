package com.itmo.wineup.network.firebase

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class CloudMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d("FCM", "New token acquired: $token")
        // Send this to server
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        val message = p0.notification!!.body
        val title = p0.notification!!.title
        Log.d("FCM", "$message, $title")
        // Do stuff when in foreground
    }

}