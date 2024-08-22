package xyz.norlib.bank305.integration

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.fnsv.bsa.sdk.BsaSdk
import com.fnsv.bsa.sdk.callback.SdkResponseCallback
import com.fnsv.bsa.sdk.response.ErrorResult
import com.fnsv.bsa.sdk.response.TokenResponse
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import xyz.norlib.bank305.R
import kotlin.random.Random

class MyFirebaseMessageService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        BsaSdk.getInstance().sdkService.responsePushMessage(remoteMessage.data)
        remoteMessage.notification?.let { message ->
            sendNotification(message)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        BsaSdk.getInstance().sdkService.registerPushToken(token,
            object : SdkResponseCallback<TokenResponse> {
                override fun onSuccess(result: TokenResponse?) {
                    Log.d("Device Token has been updated successfully", "Result code: ${result?.rtCode}")
                }
                override fun onFailed(errorResult: ErrorResult?) {
                    Log.d("Failed to update the device token", "Error code: ${errorResult?.errorCode}")
                }
            })
    }

    private fun sendNotification(message: RemoteMessage.Notification) {
        val intent = Intent(this, MyFirebaseMessageService::class.java).apply {
            addFlags(FLAG_ACTIVITY_CLEAR_TOP)
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, FLAG_IMMUTABLE
        )

        val channelId = this.getString(R.string.default_notification_channel_id)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(message.title)
            .setContentText(message.body)
            .setSmallIcon(R.mipmap.ic_305)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "305_bank", NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }
        manager.notify(Random.nextInt(), notificationBuilder.build())
    }
}