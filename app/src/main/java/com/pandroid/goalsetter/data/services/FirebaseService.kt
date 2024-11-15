package com.pandroid.goalsetter.data.services

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.pandroid.goalsetter.domain.repo.NotificationHandler
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FirebaseService : FirebaseMessagingService(), KoinComponent {

    private val notificationHandler: NotificationHandler by inject()

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "New token: $token")
        notificationHandler.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d("FCM", "Message received: ${remoteMessage.data}")

        remoteMessage.notification?.let {
            notificationHandler.showNotification(
                title = it.title.orEmpty(),
                body = it.body.orEmpty()
            )
        }
    }
}
