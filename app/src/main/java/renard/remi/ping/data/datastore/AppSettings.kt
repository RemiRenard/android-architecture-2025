package renard.remi.ping.data.datastore

import kotlinx.serialization.Serializable
import renard.remi.ping.ui.theme.Palette

@Serializable
data class AppSettings(
    val useDynamicColors: Boolean = true,
    val isInDarkMode: Boolean = true,
    val palette: Palette? = null,
    val currentLanguage: String? = null,
    val accessToken: String? = null,
    val userId: String? = null,
    val pushNotificationEnabled: Boolean = true
)