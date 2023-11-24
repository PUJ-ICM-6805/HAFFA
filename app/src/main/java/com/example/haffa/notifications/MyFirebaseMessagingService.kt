package com.example.haffa.notifications

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        Log.d("HOASDASD",token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val notificationAssets = NotificationAssets(this)

        remoteMessage.notification?.let { notification ->
            val title = notification.title ?: "Recuerda..."
            val body = notification.body ?: "Puedes utilizar HAFFA para conectarte con tus amigos"

            notificationAssets.showNotification(
                1,
                title,
                1,
                body,
                this
            )
        }
    }

}