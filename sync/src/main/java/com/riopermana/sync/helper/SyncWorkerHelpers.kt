package com.riopermana.sync.helper

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.ForegroundInfo

private const val syncWorkerNotificationId = 0
private const val syncWorkerNotificationChannelId = "stockmarket.SyncChannelID"

/**
 * Foreground information for sync on lower API levels when sync workers are being
 * run with a foreground service
 */
fun Context.foregroundInfo() : ForegroundInfo = ForegroundInfo(
    syncWorkerNotificationId,
    syncWorkNotification()
)

private fun Context.syncWorkNotification(): Notification {
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        val channel = NotificationChannel(
            syncWorkerNotificationChannelId,
            "Sync",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager

        notificationManager?.createNotificationChannel(channel)
    }

    return NotificationCompat.Builder(
        this,
        syncWorkerNotificationChannelId
    )
        .setContentTitle("Stock Market")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .build()
}