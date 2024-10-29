package com.example.travelsouvenir

import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

private const val CHANNEL_ID = "travel_souvenir_notifications"

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel() // Ensure the notification channel is created
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Handle the received message
        Log.d("FCM", "Message received from: ${remoteMessage.from}")

        if (remoteMessage.data.isNotEmpty()) {
            Log.d("FCM", "Message data payload: ${remoteMessage.data}")
            showNotification(remoteMessage.data) // Show the notification with data
        }

        remoteMessage.notification?.let {
            Log.d("FCM", "Message Notification Body: ${it.body}")
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("MyFirebaseService", "Refreshed token: $token")
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String?) {
        Log.d("MyFirebaseService", "Token sent to server: $token")
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Gallery Notifications"
            val descriptionText = "Gallery"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification(data: Map<String, String>) {
        val placeName = data["placeName"] ?: "Unknown Place" // Use a default if null
        val notificationId = 1 // Unique ID for the notification

        // Create an Intent for the deep link
        val deepLinkIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("destination", "GalleryScreen") // Destination for deep link
            putExtra("placeName", placeName)
        }

        // Create a PendingIntent for the notification
        val pendingIntent = PendingIntent.getActivity(
            this,
            notificationId,
            deepLinkIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Check notification permission before showing notification
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            // Create the notification
            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("New Place")
                .setContentText("Click to view the gallery for $placeName")
                .setSmallIcon(R.drawable.travel_photo)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

            // Show the notification
            with(NotificationManagerCompat.from(this)) {
                notify(notificationId, notification)
            }
        } else {
            Log.w("MyFirebaseService", "Notification permission not granted")
        }
    }
}
