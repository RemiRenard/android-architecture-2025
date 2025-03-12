package renard.remi.ping.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import renard.remi.ping.data.datastore.AppSettings
import renard.remi.ping.extension.dataStore

@Composable
fun PingTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    val appSettings = context.dataStore.data.collectAsState(
        initial = AppSettings(isInDarkMode = darkTheme)
    ).value

    MaterialTheme(
        colorScheme = getColorSchemeByPalette(
            context = context,
            // Use darkTheme instead of appSettings.isInDarkMode to manage the dark theme with the system.
            isDarkTheme = appSettings.isInDarkMode,
            isDynamicColors = appSettings.useDynamicColors && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S,
            palette = appSettings.palette
        ),
        typography = Typography,
        content = content
    )
}