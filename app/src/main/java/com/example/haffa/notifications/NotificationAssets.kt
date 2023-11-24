package com.example.haffa.notifications

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.haffa.R
import com.example.haffa.utils.PermissionManager

class NotificationAssets(private val context: Context) {

    private val NOTIFICATION_CHANNEL_ID = "HAFFA"

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            "HAFFA",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "TOPIC"
        }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun showNotification(id: Int, topic: String, importance: Int, description: String, context: Context) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Si el permiso no está concedido, solicitarlo
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                1
            )
        } else {
            // Si el permiso está concedido, mostrar la notificación
            buildAndShowNotification(id, topic, importance, description, context)
        }
    }

    // Método para construir y mostrar la notificación
    private fun buildAndShowNotification(
        id: Int,
        topic: String,
        importance: Int,
        description: String,
        context: Context
    ) {
        val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_logo_haffa)
            .setContentTitle("Notification Title")
            .setContentText("Notification Text")
            .setPriority(importance)

        val notificationManager = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notificationManager.notify(id, builder.build())
    }

}
