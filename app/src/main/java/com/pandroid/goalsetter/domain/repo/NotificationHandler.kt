package com.pandroid.goalsetter.domain.repo

interface NotificationHandler {
    fun onNewToken(token: String)
    fun showNotification(title: String, body: String)
}
