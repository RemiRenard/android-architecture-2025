package renard.remi.ping.data.datastore

import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    val useDynamicColors: Boolean = false,
    val accessToken: String? = null,
    val userId: String? = null,
    val pushNotificationEnabled: Boolean = true
)