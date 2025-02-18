package renard.remi.ping.service

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import renard.remi.ping.R
import renard.remi.ping.domain.repository.UserRepository
import renard.remi.ping.extension.dataStore
import javax.inject.Inject

@AndroidEntryPoint
class FCMService : FirebaseMessagingService() {

    @Inject
    lateinit var userRepository: UserRepository

    @OptIn(DelicateCoroutinesApi::class)
    override fun onMessageReceived(message: RemoteMessage) {
        GlobalScope.launch {
            message.data.let {
                if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && ContextCompat.checkSelfPermission(
                        baseContext,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED)
                ) {
                    val appSettings = baseContext.dataStore.data.firstOrNull()
                    if (appSettings?.pushNotificationEnabled == true) {
                        showLocalNotification(it)
                    }
                }
            }
        }
    }

    private fun showLocalNotification(data: MutableMap<String, String>) {
        val notification = NotificationCompat.Builder(
            applicationContext,
            applicationContext.getString(R.string.default_channel_id)
        )
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(data["title"])
            .setContentText(data["body"])
            .build()

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        GlobalScope.launch {
            Log.i("FCMService", "newToken : $token")
            userRepository.updateRemoteFcmToken(fcmToken = token)
        }
    }
}